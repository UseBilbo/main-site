package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.AddressResolverOptionsConfigurator;

import io.vertx.core.dns.AddressResolverOptions;

@Singleton
public class AddressResolverOptionsProvider extends AbstractOptionsProvider<AddressResolverOptions> {

    @Inject
    public AddressResolverOptionsProvider(Injector injector, Reflections reflections) {
        super(injector, reflections, AddressResolverOptionsConfigurator.class, "AddressResolver");
    }

    @Override
    protected AddressResolverOptions newOptions() {
        return new AddressResolverOptions();
    }
}
