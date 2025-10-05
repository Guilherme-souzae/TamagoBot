package br.inatel.cdg.tamagobot.commands.pet;

import br.inatel.cdg.tamagobot.commands.BotCommand;
import br.inatel.cdg.tamagobot.esr.ServiceFacade;
import br.inatel.cdg.tamagobot.esr.pet.PetEntity;
import br.inatel.cdg.tamagobot.esr.pet.PetService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CheckCommand extends BotCommand
{
    public CheckCommand(ServiceFacade serviceFacade)
    {
        super(serviceFacade);
    }

    @Override
    public String getName()
    {
        return "Check";
    }

    @Override
    public void execute(MessageReceivedEvent event)
    {
        String msg = event.getMessage().getContentRaw();
        String guildId = event.getGuild().getId();

        try
        {
            PetEntity petEntity = serviceFacade.getPet(guildId);
            petEntity.calculateAll();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(petEntity.getName());
            eb.setImage(petEntity.getImg_url());

            int energia = petEntity.getEnergy();
            int fome = petEntity.getHunger();

            eb.setDescription("Energia: " + energia + "\nFome: " + fome);

            event.getChannel().sendMessageEmbeds(eb.build()).queue();
            eb.clear();

        }
        catch (IllegalStateException e)
        {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
