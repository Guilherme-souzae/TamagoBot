package org.inatel.cdg.commands;

import org.inatel.cdg.Command;
import org.inatel.cdg.CommandContext;
import org.inatel.cdg.crud.PetEntity;
import org.inatel.cdg.crud.PetService;

public class LullabyCommand implements Command
{
    @Override
    public String getName()
    {
        return "lullaby";
    }

    @Override
    public String getDescription()
    {
        return "Puts the pet to sleep";
    }

    @Override
    public void execute(CommandContext ctx)
    {
        String guildId = ctx.getEvent().getGuild().getId();
        PetEntity pet = PetService.INSTANCE.GetPet(guildId);
        PetService.INSTANCE.SetSleep(pet, true);
        ctx.getEvent().getChannel().sendMessage("Pet is sleeping").queue();
    }
}
