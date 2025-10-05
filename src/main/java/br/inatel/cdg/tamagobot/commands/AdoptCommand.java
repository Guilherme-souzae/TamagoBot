package br.inatel.cdg.tamagobot.commands;

import br.inatel.cdg.tamagobot.esr.Service;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AdoptCommand extends BotCommand
{
    public AdoptCommand(Service service)
    {
        super(service);
    }

    @Override
    public String getName()
    {
        return "Adopt";
    }

    @Override
    public void execute(MessageReceivedEvent event)
    {
        String msg = event.getMessage().getContentRaw();
        String guildId = event.getGuild().getId();

        String content = msg.substring((prefix + "Adopt").length()).trim();
        try
        {
            service.createEntity(guildId, content);
            event.getChannel().sendMessage("Pet adotado!").queue();
        }
        catch (IllegalStateException | IllegalArgumentException e)
        {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
