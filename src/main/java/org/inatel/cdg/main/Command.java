package org.inatel.cdg.main;

public interface Command
{
    String getName();
    String getDescription();

    void execute(CommandContext ctx);
}
