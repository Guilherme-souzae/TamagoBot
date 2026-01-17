package org.inatel.cdg.commands;

import org.inatel.cdg.main.Command;
import org.inatel.cdg.main.CommandContext;

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
