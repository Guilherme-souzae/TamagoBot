package br.inatel.cdg.tamagobot.commands.pet;

import br.inatel.cdg.tamagobot.commands.BotCommand;
import br.inatel.cdg.tamagobot.esr.ServiceFacade;
import br.inatel.cdg.tamagobot.esr.pet.PetEntity;
import br.inatel.cdg.tamagobot.exceptions.DatabaseStateException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DisturbCommand extends BotCommand
{
    public DisturbCommand(ServiceFacade serviceFacade)
    {
        super(serviceFacade);
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
            PetEntity petEntity = serviceFacade.getPet(guildId);
            petEntity.setSleeping(false);
        }
        catch (DatabaseStateException e)
        {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
