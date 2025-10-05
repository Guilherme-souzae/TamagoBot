package br.inatel.cdg.tamagobot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry
{
    private final Map<String, BotCommand> commands = new HashMap<>();

    public void register(BotCommand command) {
        commands.put(command.getName(), command);
    }

    public void execute(String name, MessageReceivedEvent event) {
        BotCommand cmd = commands.get(name);
        if (cmd != null) cmd.execute(event);
    }
}
