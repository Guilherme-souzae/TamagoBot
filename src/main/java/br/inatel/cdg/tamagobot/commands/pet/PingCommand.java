package br.inatel.cdg.tamagobot.commands.pet;

import br.inatel.cdg.tamagobot.commands.BotCommand;
import br.inatel.cdg.tamagobot.esr.ServiceFacade;
import br.inatel.cdg.tamagobot.esr.pet.PetService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends BotCommand
{
    public PingCommand(ServiceFacade serviceFacade)
    {
        super(serviceFacade);
    }

    public String getName()
    {
        return "Ping";
    }

    public void execute(MessageReceivedEvent event)
    {
        event.getChannel().sendMessage("Pong!").queue();
    }
}
