package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand  implements Command{

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Pings the bot to check if it's online";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        long ping = event.getJDA().getGatewayPing();
        event.getJDA().getUsers().forEach(user -> System.out.println(user.getName()));
        event.reply("Pong! Gateway ping: " + ping + "ms").queue();
    }
}
