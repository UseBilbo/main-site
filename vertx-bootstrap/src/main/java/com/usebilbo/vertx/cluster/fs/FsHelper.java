package com.usebilbo.vertx.cluster.fs;

import java.io.InputStream;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.usebilbo.vertx.cluster.api.ClusterFileSystem;
import com.usebilbo.vertx.cluster.fs.impl.FsHelperImpl;

@ImplementedBy(FsHelperImpl.class)
public interface FsHelper {
    String save(ClusterFileSystem fs, InputStream source, String destination, boolean overwrite, String ext, Map<String, String> props);
    
    boolean remove(ClusterFileSystem fs, String path, boolean recursive);
    
    boolean exists(ClusterFileSystem fs, String destination);
    
    long usedSpace(ClusterFileSystem fs, String pathName);
}
