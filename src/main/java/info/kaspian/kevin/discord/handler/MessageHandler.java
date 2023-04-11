package info.kaspian.kevin.discord.handler;

import io.quarkus.runtime.Startup;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Objects;

@ApplicationScoped
@Startup
public class MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);

    private final PrivateMessageHandler privateMessageHandler;
    private final MentionRequestHandler mentionRequestHandler;

    public MessageHandler(PrivateMessageHandler privateMessageHandler, MentionRequestHandler mentionRequestHandler) {
        LOG.info("MessageHandler created");
        this.privateMessageHandler = privateMessageHandler;
        this.mentionRequestHandler = mentionRequestHandler;
    }

    public void handle(Message message) {
        if (message.getChannel().getType() == ChannelType.PRIVATE) {
            privateMessageHandler.handle(message);
        } else if (isCurrentUserMentioned(message)) {
            mentionRequestHandler.handle(message);
        }
    }

    boolean isCurrentUserMentioned(Message message) {
        String selfUserId = message.getJDA().getSelfUser().getId();
        return message.getMentions().getMembers().stream().map(Member::getId).anyMatch(id -> Objects.equals(selfUserId, id));
    }
}
