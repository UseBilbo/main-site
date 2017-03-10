package com.usebilbo.vertx.cluster.api.impl;

import java.util.concurrent.atomic.AtomicMarkableReference;

import com.usebilbo.vertx.cluster.api.IgniteProvider;
import com.usebilbo.vertx.cluster.api.LazyReference;

public class AbstractReference<T> implements LazyReference<T> {
    private final IgniteProvider provider;
    private final Instantiator<T> instantiator;
    private final AtomicMarkableReference<T> reference = new AtomicMarkableReference<>(null, false);

    public AbstractReference(IgniteProvider provider, Instantiator<T> instantiator) {
        this.provider = provider;
        this.instantiator = instantiator;
    }
    
    public static interface Instantiator<T> {
        T create(IgniteProvider provider);
    }
    
    @Override
    public T get() {
        if (!reference.isMarked()) {
            T instance = instantiator.create(provider);
            return reference.compareAndSet(null, instance, false, true) ? instance : reference.getReference();
        }
        return reference.getReference();
    }
}
