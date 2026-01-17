package org.inatel.cdg.main;

import java.util.HashMap;
import java.util.Map;

public class CommandManager
{

    private final Map<String, Command> commands = new HashMap<>();

    public void register(Command command)
    {
        commands.put(command.getName().toLowerCase(), command);
    }

    public void handle(CommandContext ctx)
    {

        String msg = ctx.getEvent().getMessage().getContentRaw();
        String prefix = "!";
        if (!msg.startsWith(prefix)) return;

        String[] split = msg.substring(prefix.length()).split("\\s+");
        String name = split[0].toLowerCase();

        Command command = commands.get(name);
        if (command == null) return;

        String[] args = split.length > 1
                ? java.util.Arrays.copyOfRange(split, 1, split.length)
                : new String[0];

        command.execute(new CommandContext(ctx.getEvent(), args));
    }
}
