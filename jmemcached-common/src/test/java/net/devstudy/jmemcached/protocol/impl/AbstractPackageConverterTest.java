package net.devstudy.jmemcached.protocol.impl;

import net.devstudy.jmemcached.exception.JMemcachedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractPackageConverterTest {

    private AbstractPackageConverter abstractPackageConverter = new AbstractPackageConverter() {
    };

    @Test
    public void checkProtocolVersionSuccess() {
        try {
            abstractPackageConverter.checkProtocolVersion((byte) 16);
        } catch (Exception e) {
            fail("Supported protocol version should be 1.0");
        }
    }

    @Test
    public void checkProtocolVersionFailed() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            abstractPackageConverter.checkProtocolVersion((byte) 0);
        });

        String expectedMessage = "Unsupported protocol version: 0.0";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getVersionByte() {
        assertEquals(16, abstractPackageConverter.getVersionByte());
    }
}