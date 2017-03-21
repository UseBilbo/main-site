package com.usebilbo.vertx.module;

import java.util.List;

import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.CollectionConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.usebilbo.vertx.annotation.ClusterManagerQueue;
import com.usebilbo.vertx.annotation.JWT;
import com.usebilbo.vertx.cluster.api.BeanParser;
import com.usebilbo.vertx.cluster.api.PersistentConfig;
import com.usebilbo.vertx.cluster.api.impl.PersistenceBeanParserImpl;
import com.usebilbo.vertx.cluster.manager.IgniteClusterManager;
import com.usebilbo.vertx.cluster.manager.impl.CollectionConfigurationProvider;
import com.usebilbo.vertx.module.provider.AddressResolverOptionsProvider;
import com.usebilbo.vertx.module.provider.CacheConfigurationProvider;
import com.usebilbo.vertx.module.provider.EventBusOptionsProvider;
import com.usebilbo.vertx.module.provider.HttpOptionsProvider;
import com.usebilbo.vertx.module.provider.IgniteConfigurationProvider;
import com.usebilbo.vertx.module.provider.IgniteProvider;
import com.usebilbo.vertx.module.provider.JwtConfigProvider;
import com.usebilbo.vertx.module.provider.MetricsOptionsProvider;
import com.usebilbo.vertx.module.provider.PersistenceConfigProvider;
import com.usebilbo.vertx.module.provider.RestBeansProvider;
import com.usebilbo.vertx.module.provider.VertxOptionsProvider;
import com.usebilbo.vertx.web.configuration.RestBean;
import com.usebilbo.vertx.web.impl.RestBeanParserImpl;

import io.vertx.core.VertxOptions;
import io.vertx.core.dns.AddressResolverOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.core.spi.cluster.ClusterManager;

public class ClusterModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CollectionConfiguration.class).annotatedWith(ClusterManagerQueue.class).toProvider(CollectionConfigurationProvider.class);
        
        bind(new TypeLiteral<BeanParser<PersistentConfig>>(){}).to(PersistenceBeanParserImpl.class);
        bind(new TypeLiteral<BeanParser<RestBean>>(){}).to(RestBeanParserImpl.class);
        bind(AddressResolverOptions.class).toProvider(AddressResolverOptionsProvider.class);
        bind(MetricsOptions.class).toProvider(MetricsOptionsProvider.class);
        bind(EventBusOptions.class).toProvider(EventBusOptionsProvider.class);
        
        bind(new TypeLiteral<List<PersistentConfig>>(){}).toProvider(PersistenceConfigProvider.class);
        bind(JsonObject.class).annotatedWith(JWT.class).toProvider(JwtConfigProvider.class);
        bind(new TypeLiteral<List<CacheConfiguration<?, ?>>>(){}).toProvider(CacheConfigurationProvider.class);
        bind(new TypeLiteral<List<RestBean>>(){}).toProvider(RestBeansProvider.class);
        bind(IgniteConfiguration.class).toProvider(IgniteConfigurationProvider.class);
        bind(Ignite.class).toProvider(IgniteProvider.class);
        
        bind(ClusterManager.class).to(IgniteClusterManager.class);
        bind(VertxOptions.class).toProvider(VertxOptionsProvider.class);
        bind(HttpServerOptions.class).toProvider(HttpOptionsProvider.class);
    }
}
