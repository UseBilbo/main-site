package com.usebilbo.vertx.bootstrap.scanner;

public interface Entry<T> {
	Class<? extends T> clazz();
}
