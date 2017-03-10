package com.usebilbo.vertx.util;

public interface ClockService {
    long now();

    boolean isBefore(long expiresAfter);
}
