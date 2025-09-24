package br.inatel.cdg.tamagobot;

import com.sun.security.ntlm.Server;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Controller extends ListenerAdapter
{
    private final String prefix = "!";

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        // Ignora mensagens de bots
        System.out.println("Message received: " + event.getMessage().getContentRaw());
        if (event.getAuthor().isBot()) return;

        // Armazena a string da mensagem
        String msg = event.getMessage().getContentRaw();

        // Armazena o ID do server
        String guildId = event.getGuild().getId();

        // Comandos

        if (msg.startsWith(prefix + "Oi"))
        {
            event.getChannel().sendMessage("Oi!").queue();
        }

        if (msg.startsWith(prefix + "Adopt"))
        {
            String content = msg.substring((prefix + "Adopt").length()).trim();
            Entity entity = Service.createEntity(guildId, content);
            Repository.create(entity);
            event.getChannel().sendMessage("Pet adotado!").queue();
        }

        if (msg.startsWith(prefix + "Check"))
        {
            String content = msg.substring((prefix + "Check").length()).trim();
            Entity entity = Repository.get(guildId);

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(entity.getName());
            eb.setImage(entity.getImg_url());
            event.getChannel().sendMessageEmbeds(eb.build()).queue();
            eb.clear();
        }
    }
}
