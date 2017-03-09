package com.usebilbo.bootstrap;

import com.usebilbo.bootstrap.injector.CoreInjector;

/**
 * Application launch context. Instance of this class is used for application bootstrapping.
 */
public interface LaunchContext {
    /**
     * Application dependency injector.
     * <p>Note that it should be accessed during bootstrapping with care, because
     * injector itself is built and configured during bootstrapping.
     */
	CoreInjector injector();

	/**
	 * Set application injector.r
	 */
	void injector(CoreInjector createChildInjector);

	/**
	 * {@link BootConfiguration} instance used to build this launch context.
	 */
    BootConfiguration configuration();
}
