package net.devstudy.jmemcached.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseTest {
    @Test
    public void toStringWithoutData() {
        Response response = new Response(Status.ADDED);
        assertEquals("ADDED", response.toString());
    }

    @Test
    public void toStringWithData() {
        Response response = new Response(Status.GOTTEN, new byte[]{1, 2, 3});
        assertEquals("GOTTEN [3 bytes]", response.toString());
    }
}