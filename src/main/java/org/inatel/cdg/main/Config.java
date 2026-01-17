package org.inatel.cdg.main;

import io.github.cdimascio.dotenv.Dotenv;

public enum Config
{
    INSTANCE;

    private static final Dotenv dotenv = Dotenv.load();

    public static String discordToken()
    {
        return dotenv.get("DISCORD_TOKEN");
    }
}
