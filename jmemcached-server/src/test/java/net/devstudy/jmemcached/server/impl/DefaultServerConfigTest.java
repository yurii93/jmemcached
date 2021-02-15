package net.devstudy.jmemcached.server.impl;

import net.devstudy.jmemcached.exception.JMemcachedException;
import net.devstudy.jmemcached.protocol.impl.DefaultRequestConverter;
import net.devstudy.jmemcached.protocol.impl.DefaultResponseConverter;
import net.devstudy.jmemcached.server.ClientSocketHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.ThreadFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DefaultServerConfigTest extends AbstractDefaultServerConfigTest {

    private DefaultServerConfig defaultServerConfig;

    @BeforeEach
    public void before() {
        defaultServerConfig = createDefaultServerConfigMock(null);
    }

    @Test
    public void testDefaultInitState() throws Exception {
        try (DefaultServerConfig defaultServerConfig = new DefaultServerConfig(null)) {
            assertEquals(DefaultRequestConverter.class, defaultServerConfig.getRequestConverter().getClass());
            assertEquals(DefaultResponseConverter.class, defaultServerConfig.getResponseConverter().getClass());
            assertEquals(DefaultStorage.class, defaultServerConfig.getStorage().getClass());
            assertEquals(DefaultCommandHandler.class, defaultServerConfig.getCommandHandler().getClass());

            assertEquals(9010, defaultServerConfig.getServerPort());
            assertEquals(1, defaultServerConfig.getInitThreadCount());
            assertEquals(10, defaultServerConfig.getMaxThreadCount());
            assertEquals(10000, defaultServerConfig.getClearDataIntervalInMs());
        }
    }

    @Test
    public void getWorkerThreadFactory() {
        ThreadFactory threadFactory = defaultServerConfig.getWorkerThreadFactory();
        Thread thread = threadFactory.newThread(mock(Runnable.class));
        assertTrue(thread.isDaemon());
        assertEquals("Worker-0", thread.getName());
    }

    @Test
    public void close() throws Exception {
        defaultServerConfig.close();
        verify(storage).close();
    }

    @Test
    public void buildNewClientSocketHandler() {
        ClientSocketHandler clientSocketHandler = defaultServerConfig.buildNewClientSocketHandler(mock(Socket.class));
        assertEquals(DefaultClientSocketHandler.class, clientSocketHandler.getClass());
    }

    @Test
    public void verifyToString() {
        assertEquals("DefaultServerConfig: port=9010, initThreadCount=1, maxThreadCount=10, clearDataIntervalInMs=10000ms", defaultServerConfig.toString());
    }

    @Test
    public void loadApplicationPropertiesNotFound() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultServerConfig.loadApplicationProperties("not_found.properties");
        });

        String expectedMessage = "Classpath resource not found: not_found.properties";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void loadApplicationPropertiesIOException() throws IOException {
        final IOException ex = new IOException("IO");

        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultServerConfig = new DefaultServerConfig(null) {
                @Override
                protected InputStream getClassPathResourceInputStream(String classPathResource) {
                    return new InputStream() {
                        @Override
                        public int read() throws IOException {
                            throw ex;
                        }
                    };
                }
            };
        });

        String expectedMessage = "Can't load application properties from classpath:server.properties";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(exception.getCause().getClass(), ex.getClass());
    }
}