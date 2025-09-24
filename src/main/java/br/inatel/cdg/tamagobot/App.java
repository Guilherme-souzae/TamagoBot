package br.inatel.cdg.tamagobot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class App
{
    public static void main(String[] args) throws Exception
    {
        // Carrega o arquivo .env
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");

        JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new Controller())
                .setActivity(Activity.playing("Diga !oi"))
                .build();
    }
}
