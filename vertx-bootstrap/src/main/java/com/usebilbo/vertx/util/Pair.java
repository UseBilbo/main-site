package com.usebilbo.vertx.util;

import java.io.Serializable;
import java.util.Objects;

public class Pair<K,V> implements Serializable {
    private static final long serialVersionUID = 4001108824209820595L;

    private final K left;
    private final V right;
    
    private Pair(K left, V right) {
        this.left = left;
        this.right = right;
    }
    
    public K left() {
        return left;
    }
    
    public V right() {
        return right;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair) {
            Pair<?,?> pair = (Pair<?,?>) obj;
            return Objects.equals(left, pair.left()) && Objects.equals(right, pair.right());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append('[').append(Objects.toString(left)).append(',').append(Objects.toString(right)).append(']').toString();
    }
    
    public static<L,R> Pair<L,R> of(L left, R right) {
        return new Pair<>(left, right);
    }
}
