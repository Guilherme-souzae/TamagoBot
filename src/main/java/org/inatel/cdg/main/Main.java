package org.inatel.cdg.main;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.inatel.cdg.commands.*;
import org.inatel.cdg.crud.HashMapDatabase;
import org.inatel.cdg.crud.PetRepository;
import org.inatel.cdg.crud.PetService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    public static void main(String[] args)
    {
        CommandManager manager = new CommandManager();
        PetServiceFacade.INSTANCE.SetPetService(new PetService(new PetRepository(new HashMapDatabase())));

        manager.register(new PingCommand());
        manager.register(new AddoptCommand());
        manager.register(new CheckCommand());
        manager.register(new LullabyCommand());
        manager.register(new ShoutCommand());
        manager.register(new TreatCommand());

        String token = Config.discordToken();

        JDABuilder.createDefault(token).enableIntents(GatewayIntent.MESSAGE_CONTENT).addEventListeners(new MessageListener(manager)).build();
    }
}