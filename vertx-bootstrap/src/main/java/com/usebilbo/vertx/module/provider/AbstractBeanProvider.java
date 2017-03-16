package com.usebilbo.vertx.module.provider;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

import org.reflections.Reflections;

import com.usebilbo.vertx.cluster.api.BeanParser;

public class AbstractBeanProvider<T> extends AbstractSingletonProvider<List<T>> {
    private final BeanParser<T> parser;
    private final Reflections reflections;
    private final Class<? extends Annotation> annotation;

    public AbstractBeanProvider(BeanParser<T> parser, Reflections reflections, Class<? extends Annotation> annotation) {
        this.parser = parser;
        this.reflections = reflections;
        this.annotation = annotation;
    }
    
    @Override
    protected List<T> create() {
        return reflections.getTypesAnnotatedWith(annotation)
                .stream()
                .sorted(ProviderHelper.ORDER_COMPARATOR)
                .map(bean -> parser.parse(bean))
                .collect(Collectors.toList());
    }
}
