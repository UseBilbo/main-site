package com.usebilbo.vertx.util.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UtcClockServiceTest {
    private static final long TIME_STAMP = 1461555018433L;
    @Mock
    private Clock clock;
    
    @Test
    public void isBeforeAfterIsWorkingProperly() throws Exception {
        UtcClockService service = new UtcClockService(clock);
        when(clock.millis()).thenReturn(TIME_STAMP, TIME_STAMP, TIME_STAMP);
        
        assertThat(service.isBefore(TIME_STAMP - 1)).isFalse();
        assertThat(service.isBefore(TIME_STAMP)).isTrue();
        assertThat(service.isBefore(TIME_STAMP + 1)).isTrue();
    }
}
