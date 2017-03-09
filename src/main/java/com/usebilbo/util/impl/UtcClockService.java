package com.usebilbo.util.impl;

import java.time.Clock;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.usebilbo.annotation.UTC;
import com.usebilbo.util.ClockService;

@Singleton
public class UtcClockService implements ClockService {
    private final Clock clock;

    @Inject
    public UtcClockService(@UTC Clock clock) {
        this.clock = clock;
    }

    @Override
    public long now() {
        return clock.millis();
    }

    @Override
    public boolean isBefore(long expiresAfter) {
        return now() <= expiresAfter;
    }
}
