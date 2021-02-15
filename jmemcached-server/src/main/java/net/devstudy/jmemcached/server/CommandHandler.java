package net.devstudy.jmemcached.server;

import net.devstudy.jmemcached.model.Request;
import net.devstudy.jmemcached.model.Response;

public interface CommandHandler {

    Response handle(Request request);
}
