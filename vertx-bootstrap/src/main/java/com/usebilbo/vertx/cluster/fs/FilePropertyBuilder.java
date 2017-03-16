package com.usebilbo.vertx.cluster.fs;

import java.util.Map;

import com.usebilbo.vertx.util.MapBuilder;

public class FilePropertyBuilder {
    private MapBuilder<String, String> builder;
    
    private FilePropertyBuilder() {
        builder = MapBuilder.create();
    }
    private FilePropertyBuilder(Map<String, String> source) {
        builder = MapBuilder.create(source);
    }
    
    public static FilePropertyBuilder create() {
        return new FilePropertyBuilder();
    }

    public static FilePropertyBuilder create(Map<String, String> source) {
        return new FilePropertyBuilder(source);
    }

    public static FilePropertyBuilder create(FilePropertyBuilder builder) {
        return new FilePropertyBuilder(builder.builder.build());
    }
    
    public FilePropertyBuilder withUser(String name) {
        builder.with(FileProperties.USER_NAME, name);
        return this;
    }

    public FilePropertyBuilder withGroup(String name) {
        builder.with(FileProperties.GROUP_NAME, name);
        return this;
    }

    public FilePropertyBuilder withPermissions(String permissions) {
        builder.with(FileProperties.PERMISSION, permissions);
        return this;
    }

    public Map<String, String> build() {
        return builder.build();
    }
}
