package com.usebilbo.vertx.cluster.configuration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.usebilbo.vertx.annotation.VertxOptionConfiguration;
import com.usebilbo.vertx.configuration.Configurator;
import com.usebilbo.vertx.properties.PropertyContainer;

import io.vertx.core.VertxOptions;
import io.vertx.core.dns.AddressResolverOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.core.spi.cluster.ClusterManager;

@Singleton
@VertxOptionConfiguration
public class ClusterManagerConfigurator implements Configurator<VertxOptions> {
    private final ClusterManager clusterManager;
    private final AddressResolverOptions addressResolverOptions;
    private int eventLoopPoolSize = 2 * Runtime.getRuntime().availableProcessors();
    private int workerPoolSize = 20;
    private int internalBlockingPoolSize = 20;
    private long blockedThreadCheckInterval = 1000L;
    private long maxEventLoopExecuteTime = 2L * 1000 * 1000000;
    private long maxWorkerExecuteTime = 60L * 1000 * 1000000;
    private int quorumSize = 1;
    private long warningExceptionTime = 5L * 1000 * 1000000;
    private boolean haEnabled = false;
    private String haGroup = "__DEFAULT__";

    private MetricsOptions metricsOptions;
    private EventBusOptions eventBusOptions;

    @Inject
    public ClusterManagerConfigurator(ClusterManager clusterManager, 
            AddressResolverOptions addressResolverOptions,
            MetricsOptions metricsOptions,
            EventBusOptions eventBusOptions,
            @Named("vertx.manager.config.eventLoopPoolSize") PropertyContainer eventLoopPoolSize, 
            @Named("vertx.manager.config.workerPoolSize") PropertyContainer workerPoolSize, 
            @Named("vertx.manager.config.internalBlockingPoolSize") PropertyContainer internalBlockingPoolSize, 
            @Named("vertx.manager.config.blockedThreadCheckInterval") PropertyContainer blockedThreadCheckInterval, 
            @Named("vertx.manager.config.maxEventLoopExecuteTime") PropertyContainer maxEventLoopExecuteTime, 
            @Named("vertx.manager.config.maxWorkerExecuteTime") PropertyContainer maxWorkerExecuteTime, 
            @Named("vertx.manager.config.quorumSize") PropertyContainer quorumSize, 
            @Named("vertx.manager.config.warningExceptionTime") PropertyContainer warningExceptionTime, 
            @Named("vertx.manager.config.haEnabled") PropertyContainer haEnabled, 
            @Named("vertx.manager.config.haGroup") PropertyContainer haGroup) {
        this.clusterManager = clusterManager;
        this.addressResolverOptions = addressResolverOptions;
        this.metricsOptions = metricsOptions;
        this.eventBusOptions = eventBusOptions;

        this.eventLoopPoolSize = eventLoopPoolSize.asInt(2 * Runtime.getRuntime().availableProcessors());
        this.workerPoolSize = workerPoolSize.asInt(20);
        this.internalBlockingPoolSize = internalBlockingPoolSize.asInt(20);
        this.blockedThreadCheckInterval = blockedThreadCheckInterval.asDuration(1000L);
        this.maxEventLoopExecuteTime = maxEventLoopExecuteTime.asDuration(2L * 1000 * 1000) * 1000L;
        this.maxWorkerExecuteTime = maxWorkerExecuteTime.asDuration(60L * 1000 * 1000) * 1000L;
        this.quorumSize = quorumSize.asInt(1);
        this.warningExceptionTime = warningExceptionTime.asDuration(5L * 1000 * 1000) * 1000L;
        this.haEnabled = haEnabled.asBool(false);
        this.haGroup = haGroup.asString("__DEFAULT__");
    }

    @Override
    public void configure(VertxOptions options) {
        options.setClusterManager(clusterManager);
        options.setAddressResolverOptions(addressResolverOptions);
        options.setMetricsOptions(metricsOptions);
        options.setEventBusOptions(eventBusOptions);
        
        options.setEventLoopPoolSize(eventLoopPoolSize);
        options.setWorkerPoolSize(workerPoolSize);
        options.setInternalBlockingPoolSize(internalBlockingPoolSize);
        options.setBlockedThreadCheckInterval(blockedThreadCheckInterval);
        options.setMaxEventLoopExecuteTime(maxEventLoopExecuteTime);
        options.setMaxWorkerExecuteTime(maxWorkerExecuteTime);
        options.setQuorumSize(quorumSize);
        options.setWarningExceptionTime(warningExceptionTime);
        options.setHAEnabled(haEnabled);
        options.setHAGroup(haGroup);
    }
}
