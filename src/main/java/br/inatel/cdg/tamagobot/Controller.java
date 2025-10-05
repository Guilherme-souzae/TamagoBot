package br.inatel.cdg.tamagobot;

import br.inatel.cdg.tamagobot.commands.*;
import br.inatel.cdg.tamagobot.esr.Entity;
import br.inatel.cdg.tamagobot.esr.Repository;
import br.inatel.cdg.tamagobot.esr.Service;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Controller extends ListenerAdapter
{
    private Service service;
    CommandRegistry registry;

    public Controller()
    {
        this.service = new Service(new Repository());
        this.registry = new CommandRegistry();

        registry.register(new PingCommand(service));
        registry.register(new AdoptCommand(service));
        registry.register(new CheckCommand(service));
        registry.register(new AbandonCommand(service));
    }

    // Setter para injeção de dependência
    public void setService(Service service)
    {
        this.service = service;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String content = event.getMessage().getContentRaw();

        if (!content.startsWith(BotCommand.prefix)) return;
        String commandName = content.substring(1).split(" ")[0];

        registry.execute(commandName, event);
    }
}
