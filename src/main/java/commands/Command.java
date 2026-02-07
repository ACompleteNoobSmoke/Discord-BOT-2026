package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;

public interface Command {
    String getName();
    String getDescription();
    void executeSlash (SlashCommandInteractionEvent event);

}
