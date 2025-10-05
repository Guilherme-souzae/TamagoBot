package br.inatel.cdg.tamagobot.commands;

import br.inatel.cdg.tamagobot.esr.pet.PetService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

abstract public class BotCommand
{
    protected PetService petService;

    public BotCommand(PetService petService)
    {
        this.petService = petService;
    }

    public static final String prefix = "!";
    public abstract String getName();

    public abstract void execute(MessageReceivedEvent event);
}
