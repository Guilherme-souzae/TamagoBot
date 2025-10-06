package br.inatel.cdg.tamagobot.commands.pet;

import br.inatel.cdg.tamagobot.commands.BotCommand;
import br.inatel.cdg.tamagobot.esr.ServiceFacade;
import br.inatel.cdg.tamagobot.exceptions.DatabaseStateException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AbandonCommand extends BotCommand
{
    public AbandonCommand(ServiceFacade serviceFacade)
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
            serviceFacade.deletePet(guildId);
            event.getChannel().sendMessage("Pet abandonado, seu monstro!").queue();
        }
        catch (DatabaseStateException e)
        {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
