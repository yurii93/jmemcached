package net.devstudy.jmemcached.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractPackageTest {

    private static AbstractPackage newInstance(byte[] array) {
        return new AbstractPackage(array) {
        };
    }

    @Test
    public void hasDataNull() {
        AbstractPackage aPackage = newInstance(null);
        assertFalse(aPackage.hasData());
    }

    @Test
    public void hasDataEmpty() {
        AbstractPackage aPackage = newInstance(new byte[0]);
        assertFalse(aPackage.hasData());
    }

    @Test
    public void hasData() {
        AbstractPackage aPackage = newInstance(new byte[]{1, 2, 3});
        assertTrue(aPackage.hasData());
    }

}