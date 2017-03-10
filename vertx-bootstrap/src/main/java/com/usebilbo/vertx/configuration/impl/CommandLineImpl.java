package com.usebilbo.vertx.configuration.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.vertx.configuration.CommandLine;

public class CommandLineImpl implements CommandLine {
    private static final Logger LOG = LogManager.getLogger();
    
    private final List<String> configs = new ArrayList<>();
    private final List<String> properties = new ArrayList<>();
    private final List<String> overrides = new ArrayList<>();
    private final List<String> parameters;

    public CommandLineImpl(String[] args) {
        this.parameters = args == null ? Collections.emptyList() : Arrays.asList(args);
        parse();
    }
    
    private static interface Op {
        void op(String value);
    }

    private void parse() {
        for (Iterator<String> iterator = parameters.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            switch(key) {
                case "-c":
                    handleParameter(iterator, key, "Configuration file name", (value) -> configs.add(value));
                    break;
                case "-D":
                    handleParameter(iterator, key, "Variable definition", (value) -> properties.add(value));
                    break;
                case "-O":
                    handleParameter(iterator, key, "Variable definition", (value) -> overrides.add(value));
                    break;
                default:
                    LOG.warn("Unknown command line option {}", key);
            }
        }
    }

    private void handleParameter(Iterator<String> iterator, String key, String message, Op op) {
        if (iterator.hasNext()) {
            op.op(iterator.next());
        } else {
            LOG.warn("{} is expected after {}", message, key);
        }
    }

    @Override
    public List<String> parameters() {
        return new ArrayList<>(parameters);
    }

    @Override
    public List<String> configs() {
        return new ArrayList<>(configs);
    }
   
    @Override
    public List<String> properties() {
        return new ArrayList<>(properties);
    }

    @Override
    public List<String> overrides() {
        return new ArrayList<>(overrides);
    }

    @Override
    public void injectConfigs(List<String> inject) {
        configs.addAll(0, inject);
    }
}
