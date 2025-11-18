package br.inatel.cdg.tamagobot.esr;

import br.inatel.cdg.tamagobot.exceptions.DatabaseStateException;

public abstract class Service
{
    protected Repository repo;

    public Service(Repository repo) {this.repo = repo;}
}
