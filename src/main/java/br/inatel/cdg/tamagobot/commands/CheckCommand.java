package br.inatel.cdg.tamagobot.commands;

import br.inatel.cdg.tamagobot.esr.Entity;
import br.inatel.cdg.tamagobot.esr.Service;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CheckCommand extends BotCommand
{
    public CheckCommand(Service service)
    {
        super(service);
    }

    @Override
    public String getName()
    {
        return "Check";
    }

    @Override
    public void execute(MessageReceivedEvent event)
    {
        String msg = event.getMessage().getContentRaw();
        String guildId = event.getGuild().getId();

        try
        {
            Entity entity = service.getEntity(guildId);
            entity.calculateAll();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(entity.getName());
            eb.setImage(entity.getImg_url());

            int energia = entity.getEnergy();
            int fome = entity.getHunger();

            eb.setDescription("Energia: " + energia + "\nFome: " + fome);

            event.getChannel().sendMessageEmbeds(eb.build()).queue();
            eb.clear();

        }
        catch (IllegalStateException e)
        {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
