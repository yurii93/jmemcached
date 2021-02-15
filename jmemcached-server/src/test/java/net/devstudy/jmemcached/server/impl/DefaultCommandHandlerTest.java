package net.devstudy.jmemcached.server.impl;

import net.devstudy.jmemcached.exception.JMemcachedException;
import net.devstudy.jmemcached.model.Command;
import net.devstudy.jmemcached.model.Request;
import net.devstudy.jmemcached.model.Response;
import net.devstudy.jmemcached.model.Status;
import net.devstudy.jmemcached.server.ServerConfig;
import net.devstudy.jmemcached.server.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DefaultCommandHandlerTest {
    private Storage storage;
    private ServerConfig serverConfig;
    private DefaultCommandHandler defaultCommandHandler;

    @BeforeEach
    public void before() {
        storage = mock(Storage.class);
        serverConfig = mock(ServerConfig.class);
        when(serverConfig.getStorage()).thenReturn(storage);
        defaultCommandHandler = new DefaultCommandHandler(serverConfig);

    }

    @Test
    public void handleClear() {
        when(storage.clear()).thenReturn(Status.CLEARED);
        Response response = defaultCommandHandler.handle(new Request(Command.CLEAR));
        assertEquals(Status.CLEARED, response.getStatus());
        assertNull(response.getData());
        verify(storage).clear();
    }

    @Test
    public void handlePut() {
        String key = "key";
        Long ttl = System.currentTimeMillis();
        byte[] data = {1, 2, 3};
        when(storage.put(key, ttl, data)).thenReturn(Status.ADDED);
        Response response = defaultCommandHandler.handle(new Request(Command.PUT, key, ttl, data));
        assertEquals(Status.ADDED, response.getStatus());
        assertNull(response.getData());
        verify(storage).put(key, ttl, data);
    }

    @Test
    public void handleRemove() {
        String key = "key";
        when(storage.remove(key)).thenReturn(Status.REMOVED);
        Response response = defaultCommandHandler.handle(new Request(Command.REMOVE, key));
        assertEquals(Status.REMOVED, response.getStatus());
        assertNull(response.getData());
        verify(storage).remove(key);
    }

    @Test
    public void handleGetNotFound() {
        String key = "key";
        when(storage.get(key)).thenReturn(null);
        Response response = defaultCommandHandler.handle(new Request(Command.GET, key));
        assertEquals(Status.NOT_FOUND, response.getStatus());
        assertNull(response.getData());
        verify(storage).get(key);
    }

    @Test
    public void handleGetFound() {
        String key = "key";
        byte[] data = {1, 2, 3};
        when(storage.get(key)).thenReturn(data);
        Response response = defaultCommandHandler.handle(new Request(Command.GET, key));
        assertEquals(Status.GOTTEN, response.getStatus());
        assertArrayEquals(data, response.getData());
        verify(storage).get(key);
    }

    @Test
    public void handleUnsupportedCommand() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultCommandHandler.handle(new Request(null));
        });

        String expectedMessage = "Unsupported command: null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}