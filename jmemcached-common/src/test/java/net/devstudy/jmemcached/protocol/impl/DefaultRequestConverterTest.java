package net.devstudy.jmemcached.protocol.impl;

import net.devstudy.jmemcached.exception.JMemcachedException;
import net.devstudy.jmemcached.model.Command;
import net.devstudy.jmemcached.model.Request;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultRequestConverterTest {
    private final DefaultRequestConverter defaultRequestConverter = new DefaultRequestConverter();

    private final byte[] requestClear = new byte[]
            //version,  command, flags
            {16, 0, 0};
    private final byte[] requestPut = new byte[]
            //version,  command, flags, key length, key bytes (123), ttl (long),               data length (int), data bytes
            {16, 1, 7, 3, 49, 50, 51, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 3, 1, 2, 3};

    @Test
    public void getFlagsByteEmpty() {
        Request request = new Request(Command.CLEAR);
        byte flags = defaultRequestConverter.getFlagsByte(request);
        assertEquals(0, flags);
    }

    @Test
    public void getFlagsByteAll() {
        Request request = new Request(Command.CLEAR, "key", System.currentTimeMillis(), new byte[]{1});
        byte flags = defaultRequestConverter.getFlagsByte(request);
        assertEquals(7, flags);
    }

    @Test
    public void writeKeySuccess() throws IOException {
        DataOutputStream dataOutputStream = spy(new DataOutputStream(mock(OutputStream.class)));
        String key = "key";
        defaultRequestConverter.writeKey(dataOutputStream, new Request(Command.GET, key));

        verify(dataOutputStream).write(key.getBytes(StandardCharsets.US_ASCII));
        verify(dataOutputStream).writeByte(3);
    }

    @Test
    public void writeKeyFailed() throws IOException {
        String key = StringUtils.repeat("a", 128);
        DataOutputStream dataOutputStream = new DataOutputStream(null);

        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultRequestConverter.writeKey(dataOutputStream, new Request(Command.GET, key));
        });

        String expectedMessage = "Key length should be <= 127 bytes for key = " + key;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void readSimpleRequest() throws IOException {
        Request request = defaultRequestConverter.readRequest(new ByteArrayInputStream(requestClear));
        assertEquals(Command.CLEAR, request.getCommand());
        assertFalse(request.hasKey());
        assertFalse(request.hasKey());
        assertFalse(request.hasKey());
    }

    @Test
    public void readComplexRequest() throws IOException {
        Request request = defaultRequestConverter.readRequest(new ByteArrayInputStream(requestPut));
        assertEquals(Command.PUT, request.getCommand());
        assertTrue(request.hasKey());
        assertEquals("123", request.getKey());
        assertTrue(request.hasTtl());
        assertEquals(Long.valueOf(5L), request.getTtl());
        assertTrue(request.hasData());
        assertArrayEquals(new byte[]{1, 2, 3}, request.getData());
    }

    @Test
    public void writeRequestWithoutData() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        defaultRequestConverter.writeRequest(out, new Request(Command.CLEAR));

        assertArrayEquals(requestClear, out.toByteArray());
    }

    @Test
    public void writeRequestWithData() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        defaultRequestConverter.writeRequest(out, new Request(Command.PUT, "123", 5L, new byte[]{1, 2, 3}));
        assertArrayEquals(requestPut, out.toByteArray());
    }

}