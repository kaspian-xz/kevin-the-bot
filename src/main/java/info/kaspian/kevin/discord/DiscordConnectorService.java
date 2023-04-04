package info.kaspian.kevin.discord;

import info.kaspian.kevin.Connector;
import info.kaspian.kevin.discord.listener.MessageListener;
import info.kaspian.kevin.discord.listener.ReadyListener;
import io.quarkus.runtime.Startup;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Startup
@ApplicationScoped
public class DiscordConnectorService implements Connector {
    private static final Logger LOG = LoggerFactory.getLogger(DiscordConnectorService.class);

    @Inject
    MessageListener messageListener;

    @Inject
    ReadyListener readyListener;

    @ConfigProperty(name = "discord.apiKey")
    String discordApiKey;

    @PostConstruct
    private void init() {
        LOG.info("Discord client start");
        JDABuilder.createDefault(discordApiKey)
                .addEventListeners(messageListener)
                .addEventListeners(readyListener)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.watching("you!"))
                .build();
    }
}
