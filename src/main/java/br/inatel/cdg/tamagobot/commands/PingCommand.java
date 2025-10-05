package br.inatel.cdg.tamagobot.commands;

import br.inatel.cdg.tamagobot.esr.pet.PetService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends BotCommand
{
    public PingCommand(PetService petService)
    {
        super(petService);
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
