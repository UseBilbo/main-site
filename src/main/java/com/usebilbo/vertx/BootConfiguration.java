package com.usebilbo.vertx;

import java.lang.annotation.Annotation;
import java.util.List;

import com.usebilbo.annotation.ConfigurationEntity;
import com.usebilbo.vertx.bootstrap.scanner.Entry;
import com.usebilbo.vertx.bootstrap.scanner.PackageScanner;

/**
 * Contained for collected configuration entries (basically classes annotated with application annotation, 
 * see {@link ConfigurationEntity} for more details) and package scanner. 
 */
public interface BootConfiguration {
	<K extends Annotation, T> List<Entry<T>> entries(Class<K> key);
	
    PackageScanner packageScanner();
}
