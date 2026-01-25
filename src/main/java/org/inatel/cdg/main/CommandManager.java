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
        // Run side events
        String id = ctx.getEvent().getGuild().getId();
        PetServiceFacade.GetPetService().Working(id);

        // Get command text
        String msg = ctx.getEvent().getMessage().getContentRaw();
        String prefix = "!";
        if (!msg.startsWith(prefix)) return;

        // Normalize command text
        String[] split = msg.substring(prefix.length()).split("\\s+");
        String name = split[0].toLowerCase();

        // Get command from text
        Command command = commands.get(name);
        if (command == null) return;

        // Get command args
        String[] args = split.length > 1
                ? java.util.Arrays.copyOfRange(split, 1, split.length)
                : new String[0];

        // Run command
        command.execute(new CommandContext(ctx.getEvent(), args));
    }
}
