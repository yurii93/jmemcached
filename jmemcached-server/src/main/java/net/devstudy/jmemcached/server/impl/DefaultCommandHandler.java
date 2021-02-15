package net.devstudy.jmemcached.server.impl;

import net.devstudy.jmemcached.exception.JMemcachedException;
import net.devstudy.jmemcached.model.Command;
import net.devstudy.jmemcached.model.Request;
import net.devstudy.jmemcached.model.Response;
import net.devstudy.jmemcached.model.Status;
import net.devstudy.jmemcached.server.CommandHandler;
import net.devstudy.jmemcached.server.ServerConfig;
import net.devstudy.jmemcached.server.Storage;

class DefaultCommandHandler implements CommandHandler {

    private final Storage storage;

    DefaultCommandHandler(ServerConfig serverConfig) {
        this.storage = serverConfig.getStorage();
    }

    @Override
    public Response handle(Request request) {
        Status status;
        byte[] data = null;
        if (request.getCommand() == Command.CLEAR) {
            status = storage.clear();
        } else if (request.getCommand() == Command.PUT) {
            status = storage.put(request.getKey(), request.getTtl(), request.getData());
        } else if (request.getCommand() == Command.REMOVE) {
            status = storage.remove(request.getKey());
        } else if (request.getCommand() == Command.GET) {
            data = storage.get(request.getKey());
            status = data == null ? Status.NOT_FOUND : Status.GOTTEN;
        } else {
            throw new JMemcachedException("Unsupported command: " + request.getCommand());
        }
        return new Response(status, data);
    }
}
