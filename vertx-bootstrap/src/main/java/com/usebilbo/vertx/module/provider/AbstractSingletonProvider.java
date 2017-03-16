package com.usebilbo.vertx.module.provider;

import java.util.concurrent.atomic.AtomicMarkableReference;

import javax.inject.Provider;

public abstract class AbstractSingletonProvider<T> implements Provider<T> {
    private final AtomicMarkableReference<T> reference = new AtomicMarkableReference<>(null, false);
    
    @Override
    public final T get() {
        if (reference.isMarked()) {
            return reference.getReference();
        }
        
        reference.set(create(), true);

        return reference.getReference();
    }

    protected abstract T create();
}
