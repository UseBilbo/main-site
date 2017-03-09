package com.usebilbo.vertx.bootstrap.property;

import java.util.List;

public interface CommandLine {
    List<String> parameters();

    List<String> configs();

    List<String> properties();

    List<String> overrides();

    void injectConfigs(List<String> collect);
}
