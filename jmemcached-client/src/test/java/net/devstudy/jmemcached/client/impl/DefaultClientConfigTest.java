package net.devstudy.jmemcached.client.impl;

import net.devstudy.jmemcached.protocol.impl.DefaultObjectSerializer;
import net.devstudy.jmemcached.protocol.impl.DefaultRequestConverter;
import net.devstudy.jmemcached.protocol.impl.DefaultResponseConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultClientConfigTest {

    private final DefaultClientConfig defaultClientConfig = new DefaultClientConfig("localhost", 9010);

    @Test
    void getHost() {
        assertEquals("localhost", defaultClientConfig.getHost());
    }

    @Test
    void getPort() {
        assertEquals(9010, defaultClientConfig.getPort());
    }

    @Test
    void getRequestConverter() {
        assertEquals(DefaultRequestConverter.class, defaultClientConfig.getRequestConverter().getClass());
    }

    @Test
    void getResponseConverter() {
        assertEquals(DefaultResponseConverter.class, defaultClientConfig.getResponseConverter().getClass());
    }

    @Test
    void getObjectSerializer() {
        assertEquals(DefaultObjectSerializer.class, defaultClientConfig.getObjectSerializer().getClass());
    }
}