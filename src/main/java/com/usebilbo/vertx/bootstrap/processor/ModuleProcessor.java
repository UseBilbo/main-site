package com.usebilbo.vertx.bootstrap.processor;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.Module;
import com.usebilbo.annotation.ApplicationModule;
import com.usebilbo.annotation.ProcessorFor;
import com.usebilbo.util.Naming;
import com.usebilbo.vertx.LaunchContext;

@Singleton
@ProcessorFor(ApplicationModule.class)
public class ModuleProcessor extends AbstractBootProcessor<ApplicationModule, Module> {
    @Inject
    public ModuleProcessor(LaunchContext context) {
        super(context, ApplicationModule.class);
    }

    @Override
    public List<Module> process() {
        List<Module> modules = instances();

        log().info("Installing modules {}", Naming.classNames(modules));

        return modules;
    }
}
