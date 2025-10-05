package br.inatel.cdg.tamagobot.commands;

import br.inatel.cdg.tamagobot.esr.pet.PetService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AbandonCommand extends BotCommand
{
    public AbandonCommand(PetService petService)
    {
        super(petService);
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
            petService.deleteEntity(guildId);
            event.getChannel().sendMessage("Pet abandonado, seu monstro!").queue();
        }
        catch (IllegalStateException e)
        {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
