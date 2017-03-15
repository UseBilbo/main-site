package com.usebilbo.vertx.module.configurator;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.name.Named;
import com.usebilbo.vertx.annotation.VertxOptionConfiguration;
import com.usebilbo.vertx.configuration.Configurator;
import com.usebilbo.vertx.properties.PropertyContainer;

import io.vertx.core.VertxOptions;

@Singleton
@VertxOptionConfiguration
public class VertxMiscConfigurator implements Configurator<VertxOptions> {
    private final int eventLoopPoolSize;
    private final int workerPoolSize;
    private final int internalBlockingPoolSize;
    private final long blockedThreadCheckInterval;
    private final long maxEventLoopExecuteTime;
    private final long maxWorkerExecuteTime;
    private final int quorumSize;
    private final long warningExceptionTime;
    private final boolean haEnabled;
    private final String haGroup;

    @Inject
    public VertxMiscConfigurator(
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
        this.eventLoopPoolSize = eventLoopPoolSize.asInt(VertxOptions.DEFAULT_EVENT_LOOP_POOL_SIZE);
        this.workerPoolSize = workerPoolSize.asInt(VertxOptions.DEFAULT_WORKER_POOL_SIZE);
        this.internalBlockingPoolSize = internalBlockingPoolSize.asInt(VertxOptions.DEFAULT_INTERNAL_BLOCKING_POOL_SIZE);
        this.blockedThreadCheckInterval = blockedThreadCheckInterval.asDuration(VertxOptions.DEFAULT_BLOCKED_THREAD_CHECK_INTERVAL);
        this.maxEventLoopExecuteTime = maxEventLoopExecuteTime.asDuration(VertxOptions.DEFAULT_MAX_EVENT_LOOP_EXECUTE_TIME) * 1000000L;
        this.maxWorkerExecuteTime = maxWorkerExecuteTime.asDuration(VertxOptions.DEFAULT_MAX_WORKER_EXECUTE_TIME) * 1000000L;
        this.quorumSize = quorumSize.asInt(VertxOptions.DEFAULT_QUORUM_SIZE);
        this.warningExceptionTime = warningExceptionTime.asDuration(5L * 1000) * 1000000L;
        this.haEnabled = haEnabled.asBool(VertxOptions.DEFAULT_HA_ENABLED);
        this.haGroup = haGroup.asString(VertxOptions.DEFAULT_HA_GROUP);
    }

    @Override
    public void configure(VertxOptions options) {
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
