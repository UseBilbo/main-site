package com.usebilbo.bootstrap.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.usebilbo.annotation.ApplicationModule;
import com.usebilbo.bootstrap.property.impl.PropertyContainerImpl;
import com.usebilbo.bootstrap.property.loader.PropertiesLoader;
import com.usebilbo.bootstrap.scanner.PackageScanner;

@ApplicationModule
public class PropertiesModule extends AbstractModule {
    private static final Logger LOG = LogManager.getLogger();

    private static final String CONFIG_EXTENSION = "helio.conf";
    
    private final CommandLine commandLine;
    private final GroupBuilder groupBuilder;
    private final PackageScanner scanner;

    @Inject
    public PropertiesModule(CommandLine commandLine, GroupBuilder groupBuilder, PackageScanner scanner) {
        this.commandLine = commandLine;
        this.groupBuilder = groupBuilder;
        this.scanner = scanner;
    }

    @Override
    protected void configure() {
        List<String> configs = scanner.resources((name) -> name.endsWith(CONFIG_EXTENSION));
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
        //TODO: replace constant with calculated at runtime value!
        result.addAll(configs.stream()
                      .filter((n) -> n.startsWith("com/usebilbo"))
                      .sorted()
                      .collect(Collectors.toList()));
        result.addAll(configs.stream()
                      .filter((n) -> !n.startsWith("com/usebilbo"))
                      .sorted()
                      .collect(Collectors.toList()));
        
        return result.stream().map(c -> PropertiesLoader.CLASSPATH_PREFIX + "/" + c).collect(Collectors.toList());
    }

    private void bindMap(String k, Map<String, List<String>> v) {
        bind(new TypeLiteral<Map<String, List<String>>>() {}).annotatedWith(Names.named(k)).toInstance(v);
    }

    private void bindVar(String k, List<String> v) {
        LOG.debug("Binding {} to {}", k, v);
        bind(PropertyContainer.class).annotatedWith(Names.named(k)).toInstance(new PropertyContainerImpl(v));
    }
}
