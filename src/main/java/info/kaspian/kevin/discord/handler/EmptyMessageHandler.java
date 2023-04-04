package info.kaspian.kevin.discord.handler;

import io.quarkus.runtime.Startup;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Startup
public class EmptyMessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EmptyMessageHandler.class);

    public EmptyMessageHandler() {
        LOG.info("EmptyMessageHandler created");
    }

    public void handle(Message message) {
        /* Ignore */
    }
}
