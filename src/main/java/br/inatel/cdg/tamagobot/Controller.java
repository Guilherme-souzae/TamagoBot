package br.inatel.cdg.tamagobot;

import br.inatel.cdg.tamagobot.commands.*;
import br.inatel.cdg.tamagobot.commands.pet.*;
import br.inatel.cdg.tamagobot.esr.ServiceFacade;
import br.inatel.cdg.tamagobot.esr.pet.IPetRepository;
import br.inatel.cdg.tamagobot.esr.pet.PetRepository;
import br.inatel.cdg.tamagobot.esr.pet.PetService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Controller extends ListenerAdapter
{
    private ServiceFacade serviceFacade;
    CommandRegistry registry;

    public Controller()
    {
        IPetRepository repo = new PetRepository();
        PetService petService = new PetService(repo);

        ServiceFacade serviceFacade = new ServiceFacade(petService);

        registry = new CommandRegistry();
        registry.register(new PingCommand(serviceFacade));
        registry.register(new AdoptCommand(serviceFacade));
        registry.register(new CheckCommand(serviceFacade));
        registry.register(new AbandonCommand(serviceFacade));
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
