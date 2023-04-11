package info.kaspian.kevin.discord.handler;

import info.kaspian.kevin.ChatModelConnectorService;
import info.kaspian.kevin.ChatRole;
import info.kaspian.kevin.HistoryItem;
import io.quarkus.runtime.Startup;
import io.reactivex.subscribers.DisposableSubscriber;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Startup
public class PrivateMessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PrivateMessageHandler.class);
    private final ChatModelConnectorService chatModelConnectorService;

    public PrivateMessageHandler(ChatModelConnectorService chatModelConnectorService) {
        LOG.info("PrivateMessageHandler created");
        this.chatModelConnectorService = chatModelConnectorService;
    }

    public void handle(Message message) {
        MessageChannelUnion channel = message.getChannel();


        if (isOwnMessage(message)) {
            return;
        }

        LinkedList<HistoryItem> historyItems = new LinkedList<>();

        List<Message> messages = message.getChannel().getHistoryBefore(message, 10).complete().getRetrievedHistory();
        messages.forEach(m -> {
            HistoryItem item = new HistoryItem(isOwnMessage(m) ? ChatRole.ASSISTANT : ChatRole.USER, m.getContentRaw());
            historyItems.addFirst(item);
        });
        historyItems.addLast(new HistoryItem(ChatRole.USER, message.getContentRaw()));

        chatModelConnectorService.request(() -> historyItems)
                .responseStream().subscribe(new DisposableSubscriber<>() {
                    private final StringBuilder sb = new StringBuilder();

                    @Override
                    protected void onStart() {
                        super.onStart();
                        channel.sendTyping().queue();
                    }

                    @Override
                    public void onNext(String s) {
                        if (sb.length() + s.length() > Message.MAX_CONTENT_LENGTH) {
                            channel.sendMessage(sb.toString()).queue();
                            channel.sendTyping().queue();
                            sb.delete(0, sb.length());
                        }
                        sb.append(s);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LOG.error("Error while reading message", throwable);
                        channel.sendMessage("Sorry, problem happened :(").queue();
                    }

                    @Override
                    public void onComplete() {
                        channel.sendMessage(sb.toString()).queue();
                    }
                });
    }

    private boolean isOwnMessage(Message message) {
        String selfUserId = message.getJDA().getSelfUser().getId();
        return Objects.equals(message.getAuthor().getId(), selfUserId);
    }
}
