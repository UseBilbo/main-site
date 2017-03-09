package com.usebilbo.ignite.configuration.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.apache.ignite.configuration.FileSystemConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.igfs.IgfsMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.annotation.GridConfigurator;
import com.usebilbo.ignite.api.ClusterFileSystem;
import com.usebilbo.ignite.configuration.IgniteConfigurator;

@Singleton
@GridConfigurator
public class IgfsConfigurator implements IgniteConfigurator {
    private static final Logger LOG = LogManager.getLogger();
    
    @Override
    public void configure(IgniteConfiguration configuration) {
        List<FileSystemConfiguration> configurations = new ArrayList<>();
        
        for (ClusterFileSystem value : ClusterFileSystem.values()) {
            configurations.add(configureSingle(value));
            LOG.info("Configuring FS \"{}\" for data cache \"{}\" and meta cache \"{}\"", value.label(), value.dataName(), value.metaName());
        }

        FileSystemConfiguration[] cfg = configurations.toArray(new FileSystemConfiguration[configurations.size()]);
        configuration.setFileSystemConfiguration(cfg);
    }

    private FileSystemConfiguration configureSingle(ClusterFileSystem value) {
        FileSystemConfiguration fsConf = new FileSystemConfiguration();
        fsConf.setDefaultMode(IgfsMode.PRIMARY);
        fsConf.setName(value.label());
        fsConf.setDataCacheName(value.dataName());
        fsConf.setMetaCacheName(value.metaName());
        fsConf.setIpcEndpointEnabled(false);
        return fsConf;
    }
}
