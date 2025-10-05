package br.inatel.cdg.tamagobot.commands;

import br.inatel.cdg.tamagobot.esr.Entity;
import br.inatel.cdg.tamagobot.esr.Service;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class FeastCommand extends BotCommand
{
    public FeastCommand(Service service)
    {
        super(service);
    }

    @Override
    public String getName()
    {
        return "Abandon";
    }

    @Override
    public void execute(MessageReceivedEvent event)
    {
        String msg = event.getMessage().getContentRaw();
        String guildId = event.getGuild().getId();

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
}
