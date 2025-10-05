package br.inatel.cdg.tamagobot.commands;

import br.inatel.cdg.tamagobot.esr.Service;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

abstract public class BotCommand
{
    protected Service service;

    public BotCommand(Service service)
    {
        this.service = service;
    }

    public static final String prefix = "!";
    public abstract String getName();

    public abstract void execute(MessageReceivedEvent event);
}
