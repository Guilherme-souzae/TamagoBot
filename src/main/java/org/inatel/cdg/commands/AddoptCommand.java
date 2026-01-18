package org.inatel.cdg.commands;

import org.inatel.cdg.main.Command;
import org.inatel.cdg.main.CommandContext;
import org.inatel.cdg.crud.PetService;
import org.inatel.cdg.utils.PetNameValidator;

public class AddoptCommand implements Command
{
    @Override
    public String getName()
    {
        return "addopt";
    }

    @Override
    public String getDescription()
    {
        return "Adopts a new pet";
    }

    @Override
    public void execute(CommandContext ctx)
    {
        String guildId = ctx.getEvent().getGuild().getId();

        String rawName = String.join(" ", ctx.getArgs());
        String petName = PetNameValidator.validate(rawName);

        long time = ctx.getEvent().getMessage().getTimeCreated().toInstant().toEpochMilli();
        String petUrl = "https://tenor.com/view/oh-oh-sexy-vampire-radio-gosha-vampire-disko-warp-gif-16581364981000675682";
        PetService.INSTANCE.CreatePet(guildId, petName, petUrl, time);
        ctx.getEvent().getChannel().sendMessage("Pet " + petName + " adotado com sucesso!").queue();
    }
}
