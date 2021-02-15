package net.devstudy.jmemcached.server.impl;

import net.devstudy.jmemcached.exception.JMemcachedException;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultServerConfigGetClearDataIntervalInMsTest extends AbstractDefaultServerConfigTest {

    public static String[][] testCases = new String[][]{
            {"999", "jmemcached.storage.clear.data.interval.ms should be >= 1000 ms"},
            {"qw", "jmemcached.storage.clear.data.interval.ms should be a number"}
    };
    private DefaultServerConfig defaultServerConfig;


    @Test
    public void getClearDataIntervalInMs() throws Exception {

        for (String[] testCase : testCases) {
            String value = testCase[0];
            String message = testCase[1];

            Properties p = new Properties();
            p.setProperty("jmemcached.storage.clear.data.interval.ms", value);

            Exception exception = assertThrows(JMemcachedException.class, () -> {
                defaultServerConfig = createDefaultServerConfigMock(p);
                defaultServerConfig.getClearDataIntervalInMs();
            });

            String actualMessage = exception.getMessage();
            assertTrue(actualMessage.contains(message));
        }
    }
}
