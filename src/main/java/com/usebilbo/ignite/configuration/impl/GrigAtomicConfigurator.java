package com.usebilbo.ignite.configuration.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.AtomicConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.usebilbo.annotation.GridConfigurator;
import com.usebilbo.ignite.configuration.IgniteConfigurator;
import com.usebilbo.vertx.bootstrap.property.PropertyContainer;

@Singleton
@GridConfigurator
public class GrigAtomicConfigurator implements IgniteConfigurator {
    private final int reserveSize;
    private final int backups;
    private final CacheMode atomicMode;

    @Inject
    public GrigAtomicConfigurator(@Named("grid.atomic.sequence.reserve.size") PropertyContainer reserveSize,
                                  @Named("grid.atomic.backups") PropertyContainer backups,
                                  @Named("grid.atomic.mode") PropertyContainer atomicMode) {
        this.reserveSize = reserveSize.asInt(AtomicConfiguration.DFLT_ATOMIC_SEQUENCE_RESERVE_SIZE);
        this.backups = reserveSize.asInt(AtomicConfiguration.DFLT_BACKUPS);
        this.atomicMode = atomicMode.as(CacheMode.class, CacheMode.PARTITIONED);
    }

    @Override
    public void configure(IgniteConfiguration configuration) {
        AtomicConfiguration atomicConfig = new AtomicConfiguration();
        atomicConfig.setAtomicSequenceReserveSize(reserveSize);
        atomicConfig.setBackups(backups);
        atomicConfig.setCacheMode(atomicMode);
        configuration.setAtomicConfiguration(atomicConfig);
    }
}
