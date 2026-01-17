package org.inatel.cdg.main;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter
{

    private final CommandManager manager;

    public MessageListener(CommandManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;

        manager.handle(new CommandContext(event, null));
    }
}

