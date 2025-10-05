package br.inatel.cdg.tamagobot.esr.player;

import br.inatel.cdg.tamagobot.esr.pet.PetEntity;

public interface IPlayerRepository
{
    void create(PlayerEntity player);
    PlayerEntity get(String guildId);
    int getBalance(String guildId);
    void delete(String guildId);
}
