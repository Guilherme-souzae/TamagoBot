package br.inatel.cdg.tamagobot.commands;

import br.inatel.cdg.tamagobot.esr.pet.PetEntity;
import br.inatel.cdg.tamagobot.esr.pet.PetService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class FeastCommand extends BotCommand
{
    public FeastCommand(PetService petService)
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
            PetEntity petEntity = petService.getEntity(guildId);
            petEntity.setSleeping(false);
            petEntity.feed(25);
        }
        catch (IllegalStateException e)
        {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
