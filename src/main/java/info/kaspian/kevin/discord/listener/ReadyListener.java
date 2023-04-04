package info.kaspian.kevin.discord.listener;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReadyListener implements EventListener {

    private static final Logger LOG = LoggerFactory.getLogger(ReadyListener.class);

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof ReadyEvent)
            LOG.info("API is ready!");
    }
}
