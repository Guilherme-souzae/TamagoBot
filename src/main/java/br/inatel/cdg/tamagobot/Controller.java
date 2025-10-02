package br.inatel.cdg.tamagobot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Controller extends ListenerAdapter
{
    private final String prefix = "!";
    private Service service;

    public Controller()
    {
        this.service = new Service();
    }

    // Setter para injeção de dependência
    public void setService(Service service)
    {
        this.service = service;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        // Ignora mensagens de bots
        System.out.println("Message received: " + event.getMessage().getContentRaw());
        if (event.getAuthor().isBot())
        {
            System.out.println("Illegal author");
            return;
        }

        // Armazena a string da mensagem
        String msg = event.getMessage().getContentRaw();

        // Armazena o ID do server
        String guildId = event.getGuild().getId();

        // Comandos

        // Oi
        if (msg.startsWith(prefix + "Oi"))
        {
            event.getChannel().sendMessage("Oi!").queue();
        }

        // Adopt
        else if (msg.startsWith(prefix + "Adopt"))
        {
            String content = msg.substring((prefix + "Adopt").length()).trim();
            try
            {
                service.createEntity(guildId, content);
                event.getChannel().sendMessage("Pet adotado!").queue();
            }
            catch (IllegalStateException | IllegalArgumentException e)
            {
                event.getChannel().sendMessage(e.getMessage()).queue();
            }
        }

        // Check
        else if (msg.startsWith(prefix + "Check"))
        {
            try
            {
                Entity entity = service.getEntity(guildId);
                entity.calculateAll();

                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(entity.getName());
                eb.setImage(entity.getImg_url());

                // supondo que os valores venham da sua entidade
                int energia = entity.getEnergy();
                int fome = entity.getHunger();

                // coloca na descrição
                eb.setDescription("Energia: " + energia + "\nFome: " + fome);

                event.getChannel().sendMessageEmbeds(eb.build()).queue();
                eb.clear();

            }
            catch (IllegalStateException e)
            {
                event.getChannel().sendMessage(e.getMessage()).queue();
            }
        }

        // Rename
        else if (msg.startsWith(prefix + "Rename"))
        {
            String content = msg.substring((prefix + "Rename").length()).trim();
            try
            {
                service.renameEntity(guildId, content);
                event.getChannel().sendMessage("Pet renomeado!").queue();
            }
            catch (IllegalStateException | IllegalArgumentException e)
            {
                event.getChannel().sendMessage(e.getMessage()).queue();
            }
        }

        // Transform
        else if (msg.startsWith(prefix + "Transform"))
        {
            String content = msg.substring((prefix + "Transform").length()).trim();
            try
            {
                service.changeImgUrl(guildId, content);
                event.getChannel().sendMessage("Pet transformado!").queue();
            }
            catch (IllegalStateException | IllegalArgumentException e)
            {
                event.getChannel().sendMessage(e.getMessage()).queue();
            }
        }

        // Lullaby
        else if (msg.startsWith(prefix + "Lullaby"))
        {
            try
            {
                Entity entity = service.getEntity(guildId);
                entity.setSleeping(true);
            }
            catch (IllegalStateException e)
            {
                event.getChannel().sendMessage(e.getMessage()).queue();
            }
        }

        // Disturb
        else if (msg.startsWith(prefix + "Disturb"))
        {
            try
            {
                Entity entity = service.getEntity(guildId);
                entity.setSleeping(false);
            }
            catch (IllegalStateException e)
            {
                event.getChannel().sendMessage(e.getMessage()).queue();
            }
        }

        // Feast
        else if (msg.startsWith(prefix + "Feast"))
        {
            try
            {
                Entity entity = service.getEntity(guildId);
                entity.setSleeping(false);
                entity.feed(25);
            }
            catch (IllegalStateException e)
            {
                event.getChannel().sendMessage(e.getMessage()).queue();
            }
        }

        // Abandon
        else if (msg.startsWith(prefix + "Abandon"))
        {
            try
            {
                service.deleteEntity(guildId);
                event.getChannel().sendMessage("Pet abandonado, seu monstro!").queue();
            }
            catch (IllegalStateException e)
            {
                event.getChannel().sendMessage(e.getMessage()).queue();
            }
        }
    }
}
