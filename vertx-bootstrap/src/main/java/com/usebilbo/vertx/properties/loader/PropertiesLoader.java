package com.usebilbo.vertx.properties.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.usebilbo.vertx.configuration.CommandLine;


public final class PropertiesLoader {
    public static final String CLASSPATH_PREFIX = "classpath:";

    private static final Logger LOG = LogManager.getLogger();
    private static final String EMPTY_VALUE = "";

    public static Map<String, List<String>> load(CommandLine commandLine) {
        return load(commandLine.configs(), commandLine.properties(), commandLine.overrides());
    }

    public static Map<String, List<String>> load(List<String> paths, List<String> properties, List<String> overrides) {
        Map<String, List<String>> result = new HashMap<>(loadAll(paths, properties));
        result.putAll(loadOverrides(overrides, result));
        return result;
    }

    private static Map<String, List<String>> loadOverrides(List<String> overrides, Map<String, List<String>> result) {
        LineProcessor processor = newLineProcessor(toProperties(result));
        overrides.forEach((prop) -> processor.process(prop));
        return processor.collected();
    }

    private static Map<String, String> toProperties(Map<String, List<String>> source) {
        Map<String, String> result = new HashMap<>();
        source.entrySet().forEach(entry -> result.put(entry.getKey(), singleValue(entry.getValue())));
        return result;
    }

    private static String singleValue(List<String> value) {
        return value.isEmpty() ? EMPTY_VALUE : value.get(0);
    }

    private static Map<String, List<String>> loadAll(List<String> paths, List<String> properties) {
        LineProcessor processor = newLineProcessor(null); 

        for (String path : paths) {
            loadSinglePath(path, processor);
        }
        processor.resetSection();
        properties.forEach((prop) -> processor.process(prop));
        
        return processor.collected();
    }

    private static LineProcessor newLineProcessor(Map<String, String> extra) {
        Properties properties = System.getProperties();
        if (extra != null) {
            properties.putAll(extra);
        }
        return new LineProcessor(properties);
    }

    private static void loadSinglePath(String path, LineProcessor lineProcessor) {
        LOG.info("Loading properties from {}", path);
        
        lineProcessor.resetSection();
        
        if (path.startsWith(CLASSPATH_PREFIX)) {
            loadFromClasspath(path.substring(CLASSPATH_PREFIX.length()), lineProcessor);
        } else {
            loadFromPath(Paths.get(path), lineProcessor);
        }
    }

    private static void loadFromClasspath(String resourcePath, LineProcessor lineProcessor) {
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(PropertiesLoader.class.getResourceAsStream(resourcePath),
                                                                          Charsets.UTF_8))) {
            rd.lines().forEach((line) -> lineProcessor.process(line));
        } catch (IOException e) {
            LOG.warn("Error \"{}\" while loading resource classpath:{}", e.getMessage(), resourcePath);
        }
    }

    private static void loadFromPath(Path filePath, LineProcessor lineProcessor) {
        try {
            Files.readAllLines(filePath).forEach((line) -> lineProcessor.process(line));
        } catch (IOException e) {
            LOG.warn("Error \"{}\" while loading resource {}", e.getMessage(), filePath);
        }
    }
}
