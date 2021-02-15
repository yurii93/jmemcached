package net.devstudy.jmemcached.protocol.impl;

import net.devstudy.jmemcached.model.Response;
import net.devstudy.jmemcached.model.Status;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DefaultResponseConverterTest {
    private final DefaultResponseConverter defaultResponseConverter = new DefaultResponseConverter();

    @Test
    public void readResponseWithoutData() throws IOException {
        Response response = defaultResponseConverter.readResponse(new ByteArrayInputStream(new byte[]
                // version, status, flags
                {16, 0, 0}));
        assertEquals(Status.ADDED, response.getStatus());
        assertFalse(response.hasData());
    }

    @Test
    public void readResponseWithData() throws IOException {
        Response response = defaultResponseConverter.readResponse(new ByteArrayInputStream(new byte[]
                // version, status, flags,  int lenght,  byte array
                {16, 0, 1, 0, 0, 0, 3, 1, 2, 3}));
        assertEquals(Status.ADDED, response.getStatus());
        assertTrue(response.hasData());
        assertArrayEquals(new byte[]{1, 2, 3}, response.getData());
    }

    @Test
    public void writeResponseWithoutData() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(Status.GOTTEN);
        defaultResponseConverter.writeResponse(outputStream, response);
        // version, status, flags
        assertArrayEquals(new byte[]{16, 2, 0}, outputStream.toByteArray());
    }

    @Test
    public void writeResponseWithData() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(Status.ADDED, new byte[]{1, 2, 3});
        defaultResponseConverter.writeResponse(outputStream, response);
        // version, status, flags,  int lenght,  byte array
        assertArrayEquals(new byte[]{16, 0, 1, 0, 0, 0, 3, 1, 2, 3}, outputStream.toByteArray());
    }
}