package com.usebilbo.util;

public interface ClockService {
    long now();

    boolean isBefore(long expiresAfter);
}
