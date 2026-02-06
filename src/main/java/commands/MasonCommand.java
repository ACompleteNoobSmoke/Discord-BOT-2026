package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class MasonCommand implements Command{
    @Override
    public String getName() {
        return "mason";
    }

    @Override
    public String getDescription() {
        return "Suprise Me";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        event.reply("https://www.youtube.com/watch?v=LWN0vQpwcKM&list=RDLWN0vQpwcKM&start_radio=1").setEphemeral(true).queue();
    }
}
