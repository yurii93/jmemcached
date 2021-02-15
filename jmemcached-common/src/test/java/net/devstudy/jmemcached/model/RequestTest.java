package net.devstudy.jmemcached.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    private Request request;

    @BeforeEach
    public void init() {
        request = new Request(Command.CLEAR);
    }

    @Test
    public void hasKeyTrue() {
        request.setKey("key");
        assertTrue(request.hasKey());
    }

    @Test
    public void hasKeyFalse() {
        assertFalse(request.hasKey());
    }

    @Test
    public void hasTtlTrue() {
        request.setTtl(System.currentTimeMillis());
        assertTrue(request.hasTtl());
    }

    @Test
    public void hasTtlFalse() {
        assertFalse(request.hasTtl());
    }

    @Test
    public void toStringClear() {
        assertEquals("CLEAR", request.toString());
    }

    @Test
    public void toStringRemove() {
        request = new Request(Command.REMOVE);
        request.setKey("key");
        assertEquals("REMOVE[key]", request.toString());
    }

    @Test
    public void toStringPut() {
        request = new Request(Command.PUT);
        request.setKey("key");
        request.setData(new byte[]{1, 2, 3});
        assertEquals("PUT[key]=3 bytes", request.toString());
    }

    @Test
    public void toStringPutWithTTL() {
        request = new Request(Command.PUT);
        request.setKey("key");
        request.setTtl(1484166240528L);
        request.setData(new byte[]{1, 2, 3});
        assertEquals("PUT[key]=3 bytes (Wed Jan 11 22:24:00 EET 2017)", request.toString());
    }

}