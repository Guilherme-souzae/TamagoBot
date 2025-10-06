package br.inatel.cdg.tamagobot.commands;

import br.inatel.cdg.tamagobot.esr.ServiceFacade;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

abstract public class BotCommand
{
    protected ServiceFacade serviceFacade;

    public BotCommand(ServiceFacade serviceFacade)
    {
        this.serviceFacade = serviceFacade;
    }

    public static final String prefix = "!";
    public abstract String getName();

    public abstract void execute(MessageReceivedEvent event);
}
