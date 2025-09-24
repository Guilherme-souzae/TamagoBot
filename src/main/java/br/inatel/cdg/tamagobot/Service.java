package br.inatel.cdg.tamagobot;

public final class Service
{
    private Service() { throw new UnsupportedOperationException("Esta classe não pode ser instanciada."); }

    public static Entity createEntity(String guildId, String msg)
    {
        int index = msg.indexOf("https://");
        String name = msg.substring(0, index).trim();
        String url = msg.substring(index).trim();

        return new Entity(guildId, name, url);
    }
}
