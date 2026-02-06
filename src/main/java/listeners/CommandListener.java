package listeners;

import commands.Command;
import commands.InfoCommand;
import commands.MasonCommand;
import commands.PingCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CommandListener extends ListenerAdapter {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CommandListener.class);
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandListener() {
        commandMap.put("ping", new PingCommand());
        commandMap.put("mason", new MasonCommand());
        commandMap.put("info", new InfoCommand());
        log.info("Registered {} commands", commandMap.size());
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        log.info("Bot is ready and connected to Discord, Logged in as {}#{}",
                event.getJDA().getSelfUser().getName(), event.getJDA().getSelfUser().getDiscriminator());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        Command command = commandMap.get(commandName);

        if (command != null) {
            log.debug("Executing slash command: {} from user: {}", commandName, event.getUser().getName());
            command.executeSlash(event);
        } else {
            log.warn("Received unknown slash command: {}", commandName);
            event.reply("Unknown command!").setEphemeral(true).queue();
        }
    }
}
