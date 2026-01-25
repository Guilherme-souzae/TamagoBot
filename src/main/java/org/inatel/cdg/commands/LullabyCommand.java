package org.inatel.cdg.commands;

import org.inatel.cdg.main.Command;
import org.inatel.cdg.main.CommandContext;
import org.inatel.cdg.crud.PetEntity;
import org.inatel.cdg.crud.PetService;
import org.inatel.cdg.main.PetServiceFacade;

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
        PetEntity pet = PetServiceFacade.GetPetService().GetPet(guildId);
        PetServiceFacade.GetPetService().SetSleep(pet, true);
        ctx.getEvent().getChannel().sendMessage("Pet is sleeping").queue();
    }
}
