package info.kaspian.kevin.discord.listener;

import info.kaspian.kevin.discord.handler.MessageHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageListener extends ListenerAdapter {
    private final MessageHandler messageHandler;

    public MessageListener(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        messageHandler.handle(event.getMessage());
    }
}
