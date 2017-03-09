package com.usebilbo.bootstrap.scanner;

import java.lang.annotation.Annotation;
import java.util.List;

import com.usebilbo.annotation.ConfigurationEntity;
import com.usebilbo.bootstrap.processor.BootProcessor;

public interface AnnotationResolver {
    /**
     * List of application annotations (see {@link ConfigurationEntity} for more details). The order of annotations 
     * returned by this method determines actual processing order of classes annotated with application annotations. 
     */
    List<Class<? extends Annotation>> annotations();

    /**
     * Helper method to resolve base type class for application annotation. 
     * See {@link ConfigurationEntity#type()} for more details.
     */
    <C> Class<C> type(Class<? extends Annotation> annotation);

    /**
     * Helper method to resolve processor class for application annotation. 
     */
    Class<? extends BootProcessor> processor(Class<? extends Annotation> annotation);
}
