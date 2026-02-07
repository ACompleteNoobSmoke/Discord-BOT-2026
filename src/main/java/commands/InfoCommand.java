package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class InfoCommand implements Command {
    @Override
    public String getName() {
        return "information";
    }

    @Override
    public String getDescription() {
        return "Get information from the BASED BOT...YEEAG BUDDY";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("BASED BOT Information");
        builder.setDescription("This is a bot created by The BASEDONE for the BASED BOT project.");
        builder.setColor(new Color(148, 0, 211));
        builder.addField("Author", "ACompleteNoobSmoke", false);
        builder.addField("Language", "Java", true);
        builder.addField("Library", "JDA (Java Discord API)", true);
        builder.setFooter("Created Just For Fun!");
        event.replyEmbeds(builder.build()).setEphemeral(true).queue();
    }
}
