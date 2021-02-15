package net.devstudy.jmemcached.model;

import net.devstudy.jmemcached.exception.JMemcachedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    public void valueOfSuccess() {
        assertEquals(Status.ADDED, Status.valueOf((byte) 0));
        assertEquals(Status.REPLACED, Status.valueOf((byte) 1));
        assertEquals(Status.GOTTEN, Status.valueOf((byte) 2));
        assertEquals(Status.NOT_FOUND, Status.valueOf((byte) 3));
        assertEquals(Status.REMOVED, Status.valueOf((byte) 4));
        assertEquals(Status.CLEARED, Status.valueOf((byte) 5));
    }

    @Test
    public void valueOfFailed() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            Status.valueOf(Byte.MAX_VALUE);
        });

        String expectedMessage = "Unsupported byteCode for Status: ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getByteCode() {
        assertEquals(0, Status.ADDED.getByteCode());
        assertEquals(1, Status.REPLACED.getByteCode());
        assertEquals(2, Status.GOTTEN.getByteCode());
        assertEquals(3, Status.NOT_FOUND.getByteCode());
        assertEquals(4, Status.REMOVED.getByteCode());
        assertEquals(5, Status.CLEARED.getByteCode());
    }

}