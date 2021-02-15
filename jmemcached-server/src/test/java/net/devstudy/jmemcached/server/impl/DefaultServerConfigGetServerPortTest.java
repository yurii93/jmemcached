package net.devstudy.jmemcached.server.impl;

import net.devstudy.jmemcached.exception.JMemcachedException;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultServerConfigGetServerPortTest extends AbstractDefaultServerConfigTest {

    public static String[][] testCases = new String[][]{
            {"-1", "jmemcached.server.port should be between 0 and 65535"},
            {"65536", "jmemcached.server.port should be between 0 and 65535"},
            {"qw", "jmemcached.server.port should be a number"}
    };

    private DefaultServerConfig defaultServerConfig;

    @Test
    public void getServerPort() throws Exception {
        for (String[] testCase : testCases) {
            String value = testCase[0];
            String message = testCase[1];

            Properties p = new Properties();
            p.setProperty("jmemcached.server.port", value);

            Exception exception = assertThrows(JMemcachedException.class, () -> {
                defaultServerConfig = createDefaultServerConfigMock(p);
                defaultServerConfig.getServerPort();
            });

            String actualMessage = exception.getMessage();
            assertTrue(actualMessage.contains(message));
        }
    }
}

