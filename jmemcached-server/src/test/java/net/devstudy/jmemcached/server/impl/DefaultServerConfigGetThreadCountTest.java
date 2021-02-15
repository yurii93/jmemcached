package net.devstudy.jmemcached.server.impl;

import net.devstudy.jmemcached.exception.JMemcachedException;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultServerConfigGetThreadCountTest extends AbstractDefaultServerConfigTest {

    public static String[][] testCases = new String[][]{
            {"0", " should be >= 1"},
            {"qw", " should be a number"}
    };

    private DefaultServerConfig defaultServerConfig;

    @Test
    public void getInitThreadCount() {
        for (String[] testCase : testCases) {
            String propertyName = "jmemcached.server.init.thread.count";
            String value = testCase[0];
            String message = testCase[1];

            Properties p = new Properties();
            p.setProperty(propertyName, value);

            Exception exception = assertThrows(JMemcachedException.class, () -> {
                defaultServerConfig = createDefaultServerConfigMock(p);
                defaultServerConfig.getInitThreadCount();
            });

            String expectedMessage = propertyName + message;
            String actualMessage = exception.getMessage();
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    @Test
    public void getMaxThreadCount() {
        for (String[] testCase : testCases) {
            String propertyName = "jmemcached.server.max.thread.count";
            String value = testCase[0];
            String message = testCase[1];

            Properties p = new Properties();
            p.setProperty(propertyName, value);

            Exception exception = assertThrows(JMemcachedException.class, () -> {
                defaultServerConfig = createDefaultServerConfigMock(p);
                defaultServerConfig.getMaxThreadCount();
            });

            String expectedMessage = propertyName + message;
            String actualMessage = exception.getMessage();
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }
}
