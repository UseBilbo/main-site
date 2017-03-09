package com.usebilbo.bootstrap.scanner;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.usebilbo.annotation.ConfigurationEntity;

/**
 * Scan specified list of packages and collect classes and resources.
 */
public interface PackageScanner {
    /**
     * Retrieve classes annotated with application annotations (see {@link ConfigurationEntity} for more details.
     */
    Map<Class<? extends Annotation>, List<Entry<?>>> scan(String[] packages);

    /**
     * Get annotation resolver for this package scanner.
     */
    AnnotationResolver resolver();

    /**
     * Retrieve resources which fulfill provided predicate.
     */
    List<String> resources(Predicate<String> predicate);
    
    /**
     * Retrieve classes annotated with specified annotation. Inherited annotations are honored if requested. 
     */
    Set<Class<?>> classes(Class<? extends Annotation> annotation, boolean honorInherited);
}
