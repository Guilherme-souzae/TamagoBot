package org.inatel.cdg.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import org.inatel.cdg.main.Command;
import org.inatel.cdg.main.CommandContext;
import org.inatel.cdg.crud.PetEntity;
import org.inatel.cdg.crud.PetService;
import org.inatel.cdg.main.PetServiceFacade;

import java.awt.*;

public class CheckCommand implements Command
{
    @Override
    public String getName()
    {
        return "check";
    }

    @Override
    public String getDescription()
    {
        return "Checks an existing pet";
    }

    @Override
    public void execute(CommandContext ctx)
    {
        String guildId = ctx.getEvent().getGuild().getId();
        long time = ctx.getEvent().getMessage().getTimeCreated().toInstant().toEpochMilli();
        PetServiceFacade.GetPetService().AgePet(guildId, time);
        PetEntity pet = PetServiceFacade.GetPetService().GetPet(guildId);

        EmbedBuilder embed = makeEmbed(ctx, pet);

        ctx.getEvent().getChannel().sendMessageEmbeds(embed.build()).queue();
    }

    private EmbedBuilder makeEmbed(CommandContext ctx, PetEntity pet)
    {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle("🐾 Seu Pet");
        embed.setColor(Color.ORANGE);

        embed.setImage("https://cdn.discordapp.com/attachments/1105665382301306901/1241600069493592164/UltraSatelite.gif?ex=696b082c&is=6969b6ac&hm=662be621913e986f1e0468b1fd0dbbca1f1ab5b4356522bf4220780ed3cbb9ee&");

        embed.addField("📛 Nome", pet.getPetName(), true);
        embed.addField("⚡ Energia", pet.getPetEnergy() + "/100", true);
        embed.addField(
                "💤 Status",
                pet.getSleeping() ? "Dormindo 😴" : "Acordado 😄",
                true
        );

        embed.setFooter(
                ctx.getEvent().getGuild().getName(),
                ctx.getEvent().getGuild().getIconUrl()
        );

        return embed;
    }
}
