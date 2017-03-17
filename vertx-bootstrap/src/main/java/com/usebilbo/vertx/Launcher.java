package com.usebilbo.vertx;

import static com.usebilbo.vertx.util.Utils.ifNotNull;
import static com.usebilbo.vertx.util.Utils.parentPackage;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.usebilbo.vertx.annotation.AppModule;
import com.usebilbo.vertx.annotation.SystemModule;
import com.usebilbo.vertx.configuration.CommandLine;
import com.usebilbo.vertx.configuration.impl.CommandLineImpl;
import com.usebilbo.vertx.configuration.impl.GuiceVerticleFactory;
import com.usebilbo.vertx.configuration.impl.VerticleManager;
import com.usebilbo.vertx.module.ClusterModule;
import com.usebilbo.vertx.module.CoreModule;
import com.usebilbo.vertx.module.PropertiesModule;
import com.usebilbo.vertx.module.VertxModule;
import com.usebilbo.vertx.properties.impl.GroupBuilderImpl;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Launcher {
    private final Reflections reflections;
    private final CommandLine commandLine;

    /**
     * Default main entry point.
     *
     * @param args
     *            the user command line arguments.
     */
    public static void main(String[] args) {
        new Launcher(new CommandLineImpl(args)).launch();
    }

    public Launcher(CommandLine commandLine) {
        this.commandLine = commandLine;
        this.reflections = new Reflections(
                new ConfigurationBuilder().forPackages(parentPackage(getClass().getPackage().getName()))
                .addScanners(new TypeAnnotationsScanner(), new ResourcesScanner()));
    }
    
    private void launch() {
        Injector bootInjector = createRootInjector();
        Vertx.clusteredVertx(bootInjector.getInstance(VertxOptions.class), (res) -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                
                Injector injector = bootInjector.createChildInjector(new VertxModule(vertx));
                injector = injector.createChildInjector(collectModules(injector, SystemModule.class));
                injector = injector.createChildInjector(collectModules(injector, AppModule.class));
                
                vertx.registerVerticleFactory(new GuiceVerticleFactory(injector));
                vertx.deployVerticle(getFullVerticleName(VerticleManager.class), new DeploymentOptions());
            }
        });
    }

    private Injector createRootInjector() {
        return Guice.createInjector(new CoreModule(reflections, commandLine), 
                new PropertiesModule(commandLine, new GroupBuilderImpl(), reflections),
                new ClusterModule());
    }

    private List<Module> collectModules(Injector injector, Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation, false).stream()
                .map(cls -> ifNotNull(injector.getInstance(cls), inst -> (Module) inst))
                .filter(mod -> mod != null)
                .collect(Collectors.toList());
    }
    
    private static String getFullVerticleName(final Class<?> verticleClazz) {
        return GuiceVerticleFactory.PREFIX + ":" + verticleClazz.getCanonicalName();
    }
}
