package com.usebilbo.vertx;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.usebilbo.util.Utils;
import com.usebilbo.vertx.annotation.AppModule;
import com.usebilbo.vertx.impl.CommandLineImpl;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * Extends {@link Launcher} to use {@link GuiceVerticleFactory} verticle factory
 * which uses Guice for verticle creation.
 *
 * <p>
 * Note: Verticle should be deployed with the factory prefix
 * {@link GuiceVerticleFactory#PREFIX} to make vertx to use registered factory
 * for verticle creation and dependency injection.
 * </p>
 * <br>
 * Borrowed from {@link https://github.com/intappx/vertx-guice/} and heavily modified.
 */
public class GuiceVertxLauncher {
    private final Reflections reflections;
    private final CommandLine commandLine;

    /**
     * Main entry point.
     *
     * @param args
     *            the user command line arguments.
     */
    public static void main(String[] args) {
        new GuiceVertxLauncher(new CommandLineImpl(args)).launch();
    }

    public GuiceVertxLauncher(CommandLine commandLine) {
        this.commandLine = commandLine;
        this.reflections = new Reflections(
                new ConfigurationBuilder().forPackages(Utils.parentPackage(getClass().getPackage().getName()))
                .addScanners(new TypeAnnotationsScanner(), new ResourcesScanner()));
    }
    
    private void launch() {
        final Injector injector = assembleInitialInjector();
        Injector appInjector = collectDerivedModules(injector.createChildInjector(collectModules(injector)));
        Vertx vertx = Vertx.vertx(appInjector.getInstance(VertxOptions.class));
        vertx.registerVerticleFactory(new GuiceVerticleFactory(appInjector.createChildInjector(new VertxModule(vertx))));
        vertx.deployVerticle(getFullVerticleName(VerticleManager.class), new DeploymentOptions());
    }

    private Injector collectDerivedModules(Injector baseInjector) {
        //TODO: add properties and run-time defined modules
        Set<? extends Module> modules = Collections.emptySet();
        return baseInjector.createChildInjector(modules);
    }

    private List<Module> collectModules(final Injector injector) {
        return reflections.getTypesAnnotatedWith(AppModule.class, false).stream()
                .map(cls -> (Module) injector.getInstance(cls)).collect(Collectors.toList());
    }

    private Injector assembleInitialInjector() {
        return Guice.createInjector(new BootstrapModule(reflections, commandLine));
    }
    
    private static String getFullVerticleName(final Class<?> verticleClazz) {
        return GuiceVerticleFactory.PREFIX + ":" + verticleClazz.getCanonicalName();
    }
}
