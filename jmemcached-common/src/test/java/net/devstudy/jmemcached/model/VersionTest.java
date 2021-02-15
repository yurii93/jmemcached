package net.devstudy.jmemcached.model;

import net.devstudy.jmemcached.exception.JMemcachedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VersionTest {

    @Test
    public void valueOfSuccess() {
        assertEquals(Version.VERSION_1_0, Version.valueOf((byte) 16));
    }

    @Test
    public void valueOfFailed() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            Version.valueOf(Byte.MAX_VALUE);
        });

        String expectedMessage = "Unsupported byteCode for Version: ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getByteCode() {
        assertEquals(16, Version.VERSION_1_0.getByteCode());
    }

    @Test
    public void verifyToString() {
        assertEquals("1.0", Version.VERSION_1_0.toString());
    }
}