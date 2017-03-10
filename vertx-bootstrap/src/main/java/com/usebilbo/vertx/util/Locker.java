package com.usebilbo.vertx.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class Locker implements AutoCloseable {
    private final Lock lock;

    private Locker(Lock lock) {
        this.lock = lock;
        lock.lock();
    }
    
    public static Locker of(Lock lock) {
        return new Locker(lock);
    }

    public static Locker forRead(ReadWriteLock lock) {
        return new Locker(lock.readLock());
    }

    public static Locker forWrite(ReadWriteLock lock) {
        return new Locker(lock.writeLock());
    }

    @Override
    public void close() {
        lock.unlock();
    }
}
