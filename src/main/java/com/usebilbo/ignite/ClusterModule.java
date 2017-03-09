package com.usebilbo.ignite;

import javax.inject.Singleton;

import org.apache.ignite.IgniteCompute;
import org.apache.ignite.IgniteEvents;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.OptionalBinder;
import com.usebilbo.annotation.ApplicationModule;
import com.usebilbo.ignite.api.IgniteProvider;
import com.usebilbo.ignite.api.LazyReference;
import com.usebilbo.ignite.api.LazyReferenceFactory;
import com.usebilbo.ignite.api.TransactionManager;
import com.usebilbo.ignite.api.impl.AbstractReference;
import com.usebilbo.ignite.api.impl.IgniteProviderImpl;
import com.usebilbo.ignite.api.impl.LazyReferenceFactoryImpl;
import com.usebilbo.ignite.api.impl.TransactionManagerImpl;
import com.usebilbo.ignite.cache.CacheConfigurationFactory;
import com.usebilbo.ignite.cache.CacheStoreFactoryProvider;
import com.usebilbo.ignite.cache.impl.DefaultCacheConfigurationFactory;
import com.usebilbo.ignite.configuration.impl.CoreIgniteConfiguration;
import com.usebilbo.ignite.fs.FsHelper;
import com.usebilbo.ignite.fs.impl.FsHelperImpl;
import com.usebilbo.ignite.persistence.mvstore.MVStoreCacheStoreFactoryProvider;

@Singleton
@ApplicationModule
public class ClusterModule extends AbstractModule {
    @Override
    protected void configure() {
        OptionalBinder.newOptionalBinder(binder(), CacheStoreFactoryProvider.class).setDefault().to(MVStoreCacheStoreFactoryProvider.class);
        OptionalBinder.newOptionalBinder(binder(), CacheConfigurationFactory.class).setDefault().to(DefaultCacheConfigurationFactory.class);

        bind(IgniteConfiguration.class).to(CoreIgniteConfiguration.class);
        bind(IgniteProvider.class).toInstance(new IgniteProviderImpl());
        bind(LazyReferenceFactory.class).to(LazyReferenceFactoryImpl.class);
        bind(TransactionManager.class).to(TransactionManagerImpl.class);
        bind(FsHelper.class).to(FsHelperImpl.class);
    }

    @Provides
    LazyReference<TransactionManager> transactionManager(IgniteProvider igniteProvider) {
        return new AbstractReference<>(igniteProvider, (p) -> new TransactionManagerImpl(p));
    }
    
    @Provides
    LazyReference<IgniteCompute> compute(IgniteProvider igniteProvider) {
        return new AbstractReference<>(igniteProvider, (p) -> p.get().compute());
    }
    
    @Provides
    LazyReference<IgniteEvents> events(IgniteProvider igniteProvider) {
        return new AbstractReference<>(igniteProvider, (p) -> p.get().events());
    }
}
