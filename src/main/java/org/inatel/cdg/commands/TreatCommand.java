package org.inatel.cdg.commands;

import org.inatel.cdg.main.*;
import org.inatel.cdg.crud.PetService;
import org.inatel.cdg.utils.PetNameValidator;

public class TreatCommand implements Command
{
    @Override
    public String getName()
    {
        return "treat";
    }

    @Override
    public String getDescription()
    {
        return "Buy a new food and feed the pet";
    }

    @Override
    public void execute(CommandContext ctx)
    {
        String guildId = ctx.getEvent().getGuild().getId();
        FoodEntity food = FoodRoulete.RollForFood();
        ctx.getEvent().getChannel().sendMessage("Yout got " + food.getName()).queue();
    }
}
