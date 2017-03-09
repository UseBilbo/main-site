package com.usebilbo.vertx.bootstrap.scanner.impl;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.google.common.base.Predicates;
import com.usebilbo.annotation.ConfigurationEntity;
import com.usebilbo.annotation.ProcessorFor;
import com.usebilbo.vertx.bootstrap.processor.BootProcessor;
import com.usebilbo.vertx.bootstrap.scanner.AnnotationResolver;
import com.usebilbo.vertx.bootstrap.scanner.Entry;
import com.usebilbo.vertx.bootstrap.scanner.PackageScanner;

@Singleton
public class ReflectionsPackageScanner implements PackageScanner {
    private static final Logger LOG = LogManager.getLogger();

    private Reflections reflections = null;

    private AnnotationResolver internalResolver;

    @Override
    public Map<Class<? extends Annotation>, List<Entry<?>>> scan(String[] packages) {
        if (reflections == null) {
            scanPackages(packages);
        }

        Map<Class<? extends Annotation>, List<Entry<?>>> result = new HashMap<>();

        for (Class<? extends Annotation> annotation : internalResolver.annotations()) {
            putAnnotation(result, annotation);
        }

        return result;
    }

    private void putAnnotation(Map<Class<? extends Annotation>, List<Entry<?>>> result, Class<? extends Annotation> annotation) {
        List<Entry<?>> classes = reflections.getTypesAnnotatedWith(annotation, false).stream()
                                            .map(cls -> new EntryImpl<>(cls)).collect(Collectors.toList());
        result.put(annotation, classes);
    }

    @SuppressWarnings("unchecked")
    private void scanPackages(String[] packages) {
        LOG.info("Packages: {}", (Object) packages);
        reflections = new Reflections(new ConfigurationBuilder().forPackages(packages)
                                      .addScanners(new TypeAnnotationsScanner(), new ResourcesScanner()));

        List<Class<? extends Annotation>> annotations = reflections.getTypesAnnotatedWith(ConfigurationEntity.class, false).stream()
                                                                   .filter(cls -> cls.isAnnotationPresent(ConfigurationEntity.class))
                                                                   .map(cls -> (Class<? extends Annotation>) cls)
                                                                   .collect(Collectors.toList());
        Set<Class<? extends BootProcessor>> processors = reflections.getTypesAnnotatedWith(ProcessorFor.class, false).stream()
                                                                    .filter(cls -> BootProcessor.class.isAssignableFrom(cls))
                                                                    .map(cls -> (Class<? extends BootProcessor>)cls)
                                                                    .collect(Collectors.toSet());

        internalResolver = new InternalAnnotationResolver(annotations, processors);
        Reflections resources = new Reflections(new ConfigurationBuilder().filterInputsBy(new FilterBuilder().include(".*")
                                                                                                             .exclude(".*class$"))
                                                                          .setScanners(new ResourcesScanner()).forPackages("/"));
        reflections.merge(resources);
    }

    @Override
    public AnnotationResolver resolver() {
        return internalResolver;
    }

    @Override
    public List<String> resources(final Predicate<String> predicate) {
        return reflections.getResources(Predicates.alwaysTrue()).stream().filter(predicate).sorted().collect(Collectors.toList());
    }

    private static class InternalAnnotationResolver implements AnnotationResolver {
        private final List<Class<? extends Annotation>> annotations;
        private final Map<Class<? extends Annotation>, Class<? extends BootProcessor>> processors;

        public InternalAnnotationResolver(List<Class<? extends Annotation>> annotations, Set<Class<? extends BootProcessor>> processors) {
            this.annotations = annotations;
            this.processors = processors.stream().collect(Collectors.toMap((cls) -> cls.getAnnotation(ProcessorFor.class).value(), (cls) -> cls));
        }

        @Override
        public List<Class<? extends Annotation>> annotations() {
            return annotations;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <C> Class<C> type(Class<? extends Annotation> annotation) {
            ConfigurationEntity conf = annotation.getAnnotation(ConfigurationEntity.class);
            return (conf == null) ? null : (Class<C>) conf.type();
        }

        @Override
        public Class<? extends BootProcessor> processor(Class<? extends Annotation> annotation) {
            return processors.get(annotation);
        }
    }
    
    @Override
    public Set<Class<?>> classes(Class<? extends Annotation> annotation, boolean honorInherited) {
        return reflections.getTypesAnnotatedWith(annotation, honorInherited);
    }
}
