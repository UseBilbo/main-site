package com.usebilbo.vertx.cluster.api;

public enum ClusterFileSystem {
//    PUBLIC("public"),
//    PRIVATE("private"),
//    TEMP("temp");
    ;
    public static final String NAME_PREFIX = "fs-";
    public static final String META_SUFFIX = "-meta";
    public static final String DATA_SUFFIX = "-data";
    
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
        this.dataName = NAME_PREFIX + label + DATA_SUFFIX;
        this.metaName = NAME_PREFIX + label + META_SUFFIX;
    }
}
