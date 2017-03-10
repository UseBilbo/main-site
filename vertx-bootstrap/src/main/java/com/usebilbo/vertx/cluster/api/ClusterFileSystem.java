package com.usebilbo.vertx.cluster.api;

public enum ClusterFileSystem {
    PUBLIC("public"),
    PRIVATE("private"),
    TEMP("temp");
    
    private final String label;
    private final String dataName;
    private final String metaName;
    
    public String label() {
        return label;
    }
    
    public String dataName() {
        return dataName;
    }

    public String metaName() {
        return metaName;
    }
    
    private ClusterFileSystem(String label) {
        this.label = label;
        this.dataName = "fs-" + label + "-data";
        this.metaName = "fs-" + label + "-meta";
    }
}
