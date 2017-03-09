package com.usebilbo.bootstrap.processor;

import java.util.List;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.usebilbo.annotation.ConfigurationEntity;
import com.usebilbo.bootstrap.scanner.Entry;

/**
 * Interface for classes which are used to build application launch context.
 * <p>
 * Typical implementation retrieves {@link Entry entries} from {@link BootConfiguration boot configuration}
 * using application annotation (see {@link ConfigurationEntity} for more details) as a key, processes them
 * and returns list of built modules suitable for {@link Injector} configuration.
 */
public interface BootProcessor {
    List<Module> process();
}
