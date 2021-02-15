package net.devstudy.jmemcached.server.impl;

import net.devstudy.jmemcached.server.Server;

import java.util.Properties;

public class JMemcachedServerFactory {

    public static Server buildNewServer(Properties overrideApplicationProperties) {
        return new DefaultServer(new DefaultServerConfig(overrideApplicationProperties));
    }
}
