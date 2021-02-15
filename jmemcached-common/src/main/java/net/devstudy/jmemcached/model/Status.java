package net.devstudy.jmemcached.model;

import net.devstudy.jmemcached.exception.JMemcachedException;

public enum Status {
    ADDED(0),
    REPLACED(1),
    GOTTEN(2),
    NOT_FOUND(3),
    REMOVED(4),
    CLEARED(5);

    private byte code;

    Status(int code) {
        this.code = (byte) code;
    }

    public static Status valueOf(byte byteCode) {
        for (Status version : Status.values()) {
            if (version.getByteCode() == byteCode) {
                return version;
            }
        }
        throw new JMemcachedException("Unsupported byteCode for Status: " + byteCode);
    }

    public byte getByteCode() {
        return code;
    }
}
