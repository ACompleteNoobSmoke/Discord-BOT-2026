package commands;

import google.YouTubeSearch;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class PlayCommand implements Command{


    private static final Logger log = LoggerFactory.getLogger(PlayCommand.class);

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getDescription() {
        return "Suprise Me";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        log.info(event.getUser().getName() + " Executed Play Command");
        String video = YouTubeSearch.searchVideo(Objects.requireNonNull(event.getOption("query")).getAsString());
        event.reply("https://www.youtube.com/watch?v=" + video).setEphemeral(true).queue();
    }
}
