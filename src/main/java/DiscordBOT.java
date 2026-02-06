import config.BotConfig;
import listeners.CommandListener;import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.commands.OptionType;import net.dv8tion.jda.api.interactions.commands.build.Commands;import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class DiscordBOT {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DiscordBOT.class);
    private static JDA jda;

    static void main() {
        String botToken = BotConfig.getBOTToken();
        if (botToken == null || botToken.isEmpty()) {
            log.error("Bot token is missing or empty. Please check your configuration file");
            return;
        }

        try {
            jda = JDABuilder.createDefault(botToken)
                    .enableIntents(EnumSet.allOf(GatewayIntent.class))
                    .addEventListeners(new CommandListener())
                    .build();

            jda.awaitReady();
            log.info("Bot is online and ready to use");

            registerSlashCommands();
        } catch (Exception e) {
            log.error("Failed to launch bot, {}", e.getMessage());
        }

    }

    private static void registerSlashCommands() {
        if (jda == null) {
            log.error("JDA instance is null. Cannot register slash commands.");
            return;
        }

        log.info("Registering slash commands...");
        jda.updateCommands()
                .addCommands(
                        Commands.slash("ping", "Check bot's latency"),
                        Commands.slash("mason", "Makes the coolest music")
//                        Commands.slash("Testing", "Lets see if this works")
                                .addOption(OptionType.STRING, "text", "The text to echo", false)

                ).queue(success -> log.info("Slash commands registered successfully"),
                        failure -> log.error("Failed to register slash commands: {}", failure.getMessage()));
    }
}
