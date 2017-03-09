package com.usebilbo.ignite.configuration.impl;

import static com.usebilbo.util.Utils.coalesce;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.ignite.configuration.IgniteConfiguration;

import com.google.inject.Inject;
import com.usebilbo.ignite.configuration.IgniteConfigurator;
import com.usebilbo.vertx.LaunchContext;

@Singleton
public class CompositeIgniteConfigurator implements IgniteConfigurator {
    private final Set<Class<? extends IgniteConfigurator>> configurators;
    private final LaunchContext context;

    @Inject
    public CompositeIgniteConfigurator(List<Class<? extends IgniteConfigurator>> configurators, LaunchContext context) {
        this.configurators = new HashSet<>(configurators);
        this.context = context;
    }

    @Override
    public void configure(IgniteConfiguration configuration) {
        Set<Class<? extends IgniteConfigurator>> configs = coalesce(() -> configurators, () -> Collections.emptySet());
        configs.stream().map(cfgClass -> context.injector().get(cfgClass)).forEach(conf -> conf.configure(configuration));
    }
}
