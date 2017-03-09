package com.usebilbo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.exception.CoreException;

public final class RunUtils {
    private static final Logger LOG = LogManager.getLogger();

    private RunUtils() {}

    public static interface Action {
        void action() throws Exception;
    }

    public static void safe(String message, RunUtils.Action action) {
        try {
            action.action();
        } catch (Exception e) {
            LOG.warn(message, e);
        }
    }

    public static void safeRethrow(String message, RunUtils.Action action) {
        try {
            action.action();
        } catch (Exception e) {
            throw CoreException.wrap(message, e);
        }
    }

    public static void safeRethrow(RunUtils.Action action) {
        try {
            action.action();
        } catch (Exception e) {
            throw CoreException.wrap(e);
        }
    }
}
