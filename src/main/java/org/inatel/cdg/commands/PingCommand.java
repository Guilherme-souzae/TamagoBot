package org.inatel.cdg.commands;

import org.inatel.cdg.Command;
import org.inatel.cdg.CommandContext;

public class PingCommand implements Command
{
    @Override
    public String getName()
    {
        return "ping";
    }

    @Override
    public String getDescription()
    {
        return "Responde com Pong";
    }

    @Override
    public void execute(CommandContext ctx)
    {
        ctx.getEvent().getChannel().sendMessage("🏓 Pong!").queue();
    }
}
