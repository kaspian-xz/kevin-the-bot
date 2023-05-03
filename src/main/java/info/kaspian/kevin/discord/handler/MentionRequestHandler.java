package info.kaspian.kevin.discord.handler;

import info.kaspian.kevin.ChatModelConnectorService;
import info.kaspian.kevin.ChatRole;
import info.kaspian.kevin.HistoryItem;
import io.quarkus.runtime.Startup;
import io.reactivex.subscribers.DisposableSubscriber;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.SelfUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

import static java.util.List.of;

@ApplicationScoped
@Startup
public class MentionRequestHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MentionRequestHandler.class);

    private final ChatModelConnectorService chatModelConnectorService;

    public MentionRequestHandler(ChatModelConnectorService chatModelConnectorService) {
        LOG.info("MentionRequestHandler created");
        this.chatModelConnectorService = chatModelConnectorService;
    }

    public void handle(Message message) {
        SelfUser selfUser = message.getJDA().getSelfUser();
        String mention = selfUser.getAsMention();
        String clearedMessage = message.getContentRaw().replaceFirst(mention, "").trim();

        chatModelConnectorService.request(() -> of(new HistoryItem(ChatRole.USER, clearedMessage)))
                .responseStream().subscribe(new DisposableSubscriber<>() {
                    private final StringBuilder sb = new StringBuilder();

                    @Override
                    protected void onStart() {
                        super.onStart();
                        message.getChannel().sendTyping().queue();
                    }

                    @Override
                    public void onNext(String s) {
                        if (sb.length() + s.length() > Message.MAX_CONTENT_LENGTH) {
                            message.reply(sb.toString()).queue();
                            message.getChannel().sendTyping().queue();
                            sb.delete(0, sb.length());
                        }
                        sb.append(s);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LOG.error("Error while reading message", throwable);
                        message.reply("Sorry, problem happened :( - {" + throwable.getMessage() + "}").queue();
                    }

                    @Override
                    public void onComplete() {
                        message.reply(sb.toString()).queue();
                    }
                });
    }
}
