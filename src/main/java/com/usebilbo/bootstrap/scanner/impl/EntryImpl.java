package com.usebilbo.bootstrap.scanner.impl;

import com.usebilbo.bootstrap.scanner.Entry;

public class EntryImpl<T> implements Entry<T> {
    private final Class<? extends T> clazz;
    
    public EntryImpl(Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class<? extends T> clazz() {
        return clazz;
    }

    @Override
    public String toString() {
        if (clazz == null) {
            return "{--empty-entry--}";
        }
        return "{" + clazz.getSimpleName() + "}";
    }
}
