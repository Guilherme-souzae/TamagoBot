package br.inatel.cdg.tamagobot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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

        // Mensagens
        if (msg.startsWith(prefix))
        {

        }
    }
}
