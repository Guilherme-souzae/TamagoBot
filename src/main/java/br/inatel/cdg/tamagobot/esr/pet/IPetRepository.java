package br.inatel.cdg.tamagobot.esr.pet;

public interface IPetRepository
{
    void create(PetEntity petEntity);
    PetEntity get(String guildId);
    String getName(String guildId);
    String getImgUrl(String guildId);
    void updateName(String guildId, String name);
    void updateUrl(String guildId, String url);
    void delete(String guildId);
}
