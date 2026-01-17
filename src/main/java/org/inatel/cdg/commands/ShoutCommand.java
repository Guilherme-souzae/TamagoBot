package org.inatel.cdg.commands;

import org.inatel.cdg.main.Command;
import org.inatel.cdg.main.CommandContext;
import org.inatel.cdg.crud.PetEntity;
import org.inatel.cdg.crud.PetService;

public class ShoutCommand implements Command
{
    @Override
    public String getName()
    {
        return "shout";
    }

    @Override
    public String getDescription()
    {
        return "Awake the pet";
    }

    @Override
    public void execute(CommandContext ctx)
    {
        String guildId = ctx.getEvent().getGuild().getId();
        PetEntity pet = PetService.INSTANCE.GetPet(guildId);
        PetService.INSTANCE.SetSleep(pet, false);
        ctx.getEvent().getChannel().sendMessage("Pet is sleeping").queue();
    }
}
