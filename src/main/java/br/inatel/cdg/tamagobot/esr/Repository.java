package br.inatel.cdg.tamagobot.esr;

import br.inatel.cdg.tamagobot.exceptions.DatabaseStateException;

public abstract class Repository
{
    public abstract void create(Entity entity) throws DatabaseStateException;
    public abstract Entity get(String id) throws DatabaseStateException;
    public abstract void update(Entity entity) throws DatabaseStateException;
    public abstract void delete(String id) throws DatabaseStateException;
}
