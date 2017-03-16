package com.usebilbo.vertx.cluster.configuration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.AtomicConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.usebilbo.vertx.annotation.ClusterConfigurator;
import com.usebilbo.vertx.configuration.Configurator;
import com.usebilbo.vertx.properties.PropertyContainer;

@Singleton
@ClusterConfigurator
public class ClusterAtomicConfigurator implements Configurator<IgniteConfiguration> {
    private final int reserveSize;
    private final int backups;
    private final CacheMode atomicMode;

    @Inject
    public ClusterAtomicConfigurator(@Named("vertx.cluster.atomic.sequence.reserve.size") PropertyContainer reserveSize,
                                  @Named("vertx.cluster.atomic.backups") PropertyContainer backups,
                                  @Named("vertx.cluster.atomic.mode") PropertyContainer atomicMode) {
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
