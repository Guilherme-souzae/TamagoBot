package br.inatel.cdg.tamagobot.commands.pet;

import br.inatel.cdg.tamagobot.commands.BotCommand;
import br.inatel.cdg.tamagobot.esr.ServiceFacade;
import br.inatel.cdg.tamagobot.esr.pet.PetService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TransformCommand extends BotCommand
{
    public TransformCommand(ServiceFacade serviceFacade)
    {
        super(serviceFacade);
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

        String content = msg.substring((prefix + "Transform").length()).trim();
        try
        {
            serviceFacade.changePetImgUrl(guildId, content);
            event.getChannel().sendMessage("Pet transformado!").queue();
        }
        catch (IllegalStateException | IllegalArgumentException e)
        {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
