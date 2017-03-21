package com.usebilbo.vertx.util;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LockerTest {
    @Mock
    private Lock lock;
    @Mock
    private ReadWriteLock rwLock;

    @Test
    public void lockIsAsquiredInConstructorAndReleasedInClose() throws Exception {
        Locker locker = Locker.of(lock);
        verify(lock).lock();
        
        locker.close();
        verify(lock).unlock();

        verifyNoMoreInteractions(lock);
    }

    @Test
    public void readLockIsUsedIfRequested() throws Exception {
        when(rwLock.readLock()).thenReturn(lock);
        
        Locker locker = Locker.forRead(rwLock);
        
        verify(rwLock).readLock();
        verify(lock).lock();
        
        locker.close();
        verify(lock).unlock();

        verifyNoMoreInteractions(lock, rwLock);
    }

    @Test
    public void writeLockIsUsedIfRequested() throws Exception {
        when(rwLock.writeLock()).thenReturn(lock);
        
        Locker locker = Locker.forWrite(rwLock);
        
        verify(rwLock).writeLock();
        verify(lock).lock();
        
        locker.close();
        verify(lock).unlock();

        verifyNoMoreInteractions(lock, rwLock);
    }
}
