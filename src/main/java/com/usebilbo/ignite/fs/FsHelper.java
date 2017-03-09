package com.usebilbo.ignite.fs;

import java.io.InputStream;
import java.util.Map;

import com.usebilbo.ignite.api.ClusterFileSystem;

public interface FsHelper {
    String save(ClusterFileSystem fs, InputStream source, String destination, boolean overwrite, String ext, Map<String, String> props);
    boolean remove(ClusterFileSystem fs, String path, boolean recursive);
    boolean exists(ClusterFileSystem fs, String destination);
    long usedSpace(ClusterFileSystem fs, String pathName);
}
