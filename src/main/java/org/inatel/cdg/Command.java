package org.inatel.cdg;

public interface Command
{
    String getName();
    String getDescription();

    void execute(CommandContext ctx);
}
