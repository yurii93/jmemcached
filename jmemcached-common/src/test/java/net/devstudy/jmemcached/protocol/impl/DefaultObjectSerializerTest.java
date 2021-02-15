package net.devstudy.jmemcached.protocol.impl;

import net.devstudy.jmemcached.exception.JMemcachedException;
import net.devstudy.jmemcached.test.SerializableFailedClass;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DefaultObjectSerializerTest {

    private final DefaultObjectSerializer defaultObjectSerializer = new DefaultObjectSerializer();
    private final Object testObject = "Test";
    //Byte array for testObject instance
    private final byte[] testObjectArray = {-84, -19, 0, 5, 116, 0, 4, 84, 101, 115, 116};
    //Byte array for a.B (class not found) instance
    private final byte[] testClassNotFoundArray =
            {-84, -19, 0, 5, 115, 114, 0, 3, 97, 46, 66, 56, 54, 57, -101, -3, 120, 66, 4, 2, 0, 0, 120, 112};
    //Byte array for test.SeriazableFailedClass instance
    private final byte[] testExceptionDuringSerialization = {-84, -19, 0, 5, 115, 114, 0, 52, 110, 101, 116, 46, 100,
            101, 118, 115, 116, 117, 100, 121, 46, 106, 109, 101, 109, 99, 97, 99, 104, 101, 100, 46, 116, 101, 115,
            116, 46, 83, 101, 114, 105, 97, 108, 105, 122, 97, 98, 108, 101, 70, 97, 105, 108, 101, 100, 67, 108, 97,
            115, 115, -21, 49, -121, 36, 59, 84, -126, 110, 2, 0, 0, 120, 112};

    @Test
    public void toByteArraySuccess() {
        byte[] actual = defaultObjectSerializer.toByteArray(testObject);
        assertArrayEquals(testObjectArray, actual);
    }

    @Test
    public void toByteArrayNull() {
        assertNull(defaultObjectSerializer.toByteArray(null));
    }

    @Test
    public void toByteArraySerializableException() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultObjectSerializer.toByteArray(new Object());
        });

        String expectedMessage = "Class java.lang.Object should implement java.io.Serializable interface";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void toByteArrayIOException() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultObjectSerializer.toByteArray(new SerializableFailedClass());
        });

        String expectedMessage = "Can't convert object to byte array: Write IO";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(exception.getCause().getClass(), IOException.class);
    }

    @Test
    public void fromByteArraySuccess() {
        String actual = (String) defaultObjectSerializer.fromByteArray(testObjectArray);
        assertEquals(testObject, actual);
    }

    @Test
    public void fromByteArrayNull() {
        assertNull(defaultObjectSerializer.fromByteArray(null));
    }

    @Test
    public void fromByteArrayIOException() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultObjectSerializer.fromByteArray(testExceptionDuringSerialization);
        });

        String expectedMessage = "Can't convert byte array to object: Read IO";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(exception.getCause().getClass(), IOException.class);
    }

    @Test
    public void fromByteArrayClassNotFoundException() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultObjectSerializer.fromByteArray(testClassNotFoundArray);
        });

        String expectedMessage = "Can't convert byte array to object: a.B";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(exception.getCause().getClass(), ClassNotFoundException.class);
    }
}