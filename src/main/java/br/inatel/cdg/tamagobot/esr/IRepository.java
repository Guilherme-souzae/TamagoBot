package br.inatel.cdg.tamagobot.esr;

public interface IRepository
{
    void create(Entity entity);
    Entity get(String guildId);
    String getName(String guildId);
    String getImgUrl(String guildId);
    void updateName(String guildId, String name);
    void updateUrl(String guildId, String url);
    void delete(String guildId);
}
