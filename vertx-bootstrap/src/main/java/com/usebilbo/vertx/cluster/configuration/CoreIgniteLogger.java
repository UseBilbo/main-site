package com.usebilbo.vertx.cluster.configuration;

import javax.annotation.Nullable;

import org.apache.ignite.IgniteLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CoreIgniteLogger implements IgniteLogger {
    private final Logger implementation;

    public CoreIgniteLogger() {
        this(LogManager.getLogger(LogManager.ROOT_LOGGER_NAME));
    }
    
    public CoreIgniteLogger(Logger implementation) {
        this.implementation = implementation;
    }

    @Override
    public IgniteLogger getLogger(Object ctgr) {
        Logger impl = ctgr == null ? LogManager.getLogger(LogManager.ROOT_LOGGER_NAME)
                                   : ctgr instanceof Class ? LogManager.getLogger(((Class<?>) ctgr).getName())
                                                           : LogManager.getLogger(ctgr.toString());

        return new CoreIgniteLogger(impl);
    }

    @Override
    public void trace(String msg) {
        implementation.trace(msg);
    }

    @Override
    public void debug(String msg) {
        implementation.debug(msg);
    }

    @Override
    public void info(String msg) {
        implementation.info(msg);
    }

    @Override
    public void warning(String msg) {
        implementation.warn(msg);
    }

    @Override
    public void warning(String msg, Throwable e) {
        implementation.warn(msg, e);
    }

    @Override
    public void error(String msg) {
        implementation.error(msg);
    }

    @Override
    public void error(String msg, Throwable e) {
        implementation.error(msg, e);
    }

    @Override
    public boolean isTraceEnabled() {
        return implementation.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return implementation.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return implementation.isInfoEnabled();
    }

    @Override
    public boolean isQuiet() {
        return !isInfoEnabled() && !isDebugEnabled();
    }

    @Override
    @Nullable
    public String fileName() {
        return null;
    }
}
