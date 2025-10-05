package br.inatel.cdg.tamagobot.commands.player;

import br.inatel.cdg.tamagobot.commands.BotCommand;
import br.inatel.cdg.tamagobot.esr.ServiceFacade;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RegisterCommand extends BotCommand
{

    public RegisterCommand(ServiceFacade serviceFacade)
    {
        super(serviceFacade);
    }

    @Override
    public String getName()
    {
        return "Register";
    }

    @Override
    public void execute(MessageReceivedEvent event)
    {
        serviceFacade.createPlayer(event.getAuthor().getId());
        event.getChannel().sendMessage("Player registrado!").queue();
    }
}
