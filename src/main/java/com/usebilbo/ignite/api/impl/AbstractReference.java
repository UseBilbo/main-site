package com.usebilbo.ignite.api.impl;

import java.util.concurrent.atomic.AtomicMarkableReference;

import com.usebilbo.ignite.api.IgniteProvider;
import com.usebilbo.ignite.api.LazyReference;

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
