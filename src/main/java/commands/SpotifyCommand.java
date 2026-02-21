package commands;

import google.YouTubeSearch;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.NewsChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.internal.entities.channel.concrete.TextChannelImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Text;
import spotify.SpotifyClient;

import java.awt.*;
import java.util.Objects;

public class SpotifyCommand implements Command{


    private static final Logger log = LoggerFactory.getLogger(SpotifyCommand.class);
    private final String MUSIC_PREFIX = "https://open.spotify.com/track/";
    private final String FAIL_URL = "https://tenor.com/view/interrogate-interrogation-you-done-fucked-up-done-fucked-up-now-you-see-now-gif-13098562";

    @Override
    public String getName() {
        return "play music";
    }

    @Override
    public String getDescription() {
        return "Suprise Me";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        log.info(event.getUser().getName() + " Executed Spotify Command");
        String artist = Objects.requireNonNull(event.getOption("artist")).getAsString();
        String title = Objects.requireNonNull(event.getOption("title")).getAsString();
        String videoID = SpotifyClient.searchSpotify(artist.trim(), title.trim());
        if (videoID == null) {
            event.reply(FAIL_URL).setEphemeral(false).queue();
            return;
        }
//        EmbedBuilder builder = new EmbedBuilder();
//        builder.setTitle("BASED BOT MUSIC");
//        builder.setDescription("This BASEDBOT has found your track");
//        builder.setColor(new Color(148, 0, 211));
//        builder.addField("Arist", artist.toUpperCase(), true);
//        builder.addField("Title", title.toUpperCase(), true);
//        builder.addField("Track", MUSIC_PREFIX.concat(videoID), false);
//        builder.setFooter("Enjoy!");
//        event.replyEmbeds(builder.build()).setEphemeral(true).queue();
        event.reply(MUSIC_PREFIX.concat(videoID)).setEphemeral(false).queue();
    }
}
