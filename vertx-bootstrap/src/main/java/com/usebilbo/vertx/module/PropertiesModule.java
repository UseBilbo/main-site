package com.usebilbo.vertx.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.google.common.base.Predicates;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.usebilbo.vertx.annotation.BootModule;
import com.usebilbo.vertx.configuration.CommandLine;
import com.usebilbo.vertx.properties.GroupBuilder;
import com.usebilbo.vertx.properties.PropertyContainer;
import com.usebilbo.vertx.properties.impl.PropertyContainerImpl;
import com.usebilbo.vertx.properties.loader.PropertiesLoader;

@BootModule
@Singleton
public class PropertiesModule extends AbstractModule {
    private static final Logger LOG = LogManager.getLogger();

    private static final String PATH_PREFIX = calculatePrefix();
    private static final String CONFIG_EXTENSION = "boot.conf";
    
    private final CommandLine commandLine;
    private final GroupBuilder groupBuilder;
    private final Reflections reflections;

    @Inject
    public PropertiesModule(CommandLine commandLine, GroupBuilder groupBuilder, Reflections reflections) {
        this.commandLine = commandLine;
        this.groupBuilder = groupBuilder;
        this.reflections = reflections;
    }

    @Override
    protected void configure() {
        List<String> configs = reflections.getResources(Predicates.alwaysTrue()).stream().filter((name) -> name.endsWith(CONFIG_EXTENSION)).sorted().collect(Collectors.toList());
        
        LOG.info("Configuration files found in classpath: {}", configs);
        
        if (!configs.isEmpty()) {
            commandLine.injectConfigs(prepareConfigName(configs));
        }
        
        Map<String, List<String>> properties = PropertiesLoader.load(commandLine);
        properties.forEach((k, v) -> bindVar(k, v));
        groupBuilder.buildGroups(properties).forEach((k, v) -> bindMap(k, v));
    }

    private List<String> prepareConfigName(List<String> configs) {
        List<String> result = new ArrayList<String>();

        result.addAll(filter(configs, with(PATH_PREFIX)));
        result.addAll(filter(configs, without(PATH_PREFIX)));
        
        return result.stream().map(c -> PropertiesLoader.CLASSPATH_PREFIX + "/" + c).collect(Collectors.toList());
    }

    private static String calculatePrefix() {
        String[] parts = PropertiesModule.class.getPackage().getName().split("\\.", 3);
        return new StringBuilder(parts[0]).append('/').append(parts[1]).toString();
    }

    private static Predicate<? super String> without(String prefix) {
        return (n) -> !n.startsWith(prefix);
    }

    private static Predicate<? super String> with(String prefix) {
        return (n) -> n.startsWith(prefix);
    }

    private List<String> filter(List<String> configs, Predicate<? super String> predicate) {
        return configs.stream().filter(predicate).sorted().collect(Collectors.toList());
    }

    private void bindMap(String k, Map<String, List<String>> v) {
        bind(new TypeLiteral<Map<String, List<String>>>() {}).annotatedWith(Names.named(k)).toInstance(v);
    }

    private void bindVar(String k, List<String> v) {
        LOG.debug("Binding {} to {}", k, v);
        bind(PropertyContainer.class).annotatedWith(Names.named(k)).toInstance(new PropertyContainerImpl(v));
    }
}
