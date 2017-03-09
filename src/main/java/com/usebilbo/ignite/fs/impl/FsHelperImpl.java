package com.usebilbo.ignite.fs.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteException;
import org.apache.ignite.IgniteFileSystem;
import org.apache.ignite.igfs.IgfsFile;
import org.apache.ignite.igfs.IgfsOutputStream;
import org.apache.ignite.igfs.IgfsPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.exception.CoreException;
import com.usebilbo.ignite.api.ClusterFileSystem;
import com.usebilbo.ignite.api.LazyReference;
import com.usebilbo.ignite.api.LazyReferenceFactory;
import com.usebilbo.ignite.fs.FsHelper;
import com.usebilbo.util.CvtUtils;
import com.usebilbo.util.FileNameUtils;
import com.usebilbo.util.Utils;

@Singleton
public class FsHelperImpl implements FsHelper {
    private static final Logger LOG = LogManager.getLogger();
    
    private static final String FILE_NAME_SEQUENCE = "fs-helper.sequence";

    private final LazyReferenceFactory factory;
    private final LazyReference<IgniteAtomicSequence> sequence;

    @Inject
    public FsHelperImpl(LazyReferenceFactory factory) {
        this.factory = factory;
        this.sequence = factory.sequence(FILE_NAME_SEQUENCE);
    }

    @Override
    public String save(ClusterFileSystem fs, InputStream source, String destination, boolean overwrite, String ext, Map<String, String> props) {
        LazyReference<IgniteFileSystem> destFs = factory.fs(fs);
        AtomicReference<String> newName = new AtomicReference<String>(null);
        
        try (OutputStream os = openFile(destination, overwrite, destFs, newName, ext)) {
            Utils.copy(source, os);
            os.flush();
            os.close();
        } catch (IgniteException | IOException e) {
            throw CoreException.wrap(e);
        }
        
        try {
            IgfsPath path = new IgfsPath(newName.get());
            destFs.get().update(path, props);
            IgfsFile info = destFs.get().info(path);
            LOG.info("File {} properties: {}", path, info.properties());
        } catch (IgniteException e) {
            throw CoreException.wrap(e);
        }
        
        return newName.get();
    }

    private IgfsOutputStream openFile(String destination, 
                                      boolean overwrite, 
                                      LazyReference<IgniteFileSystem> fs, 
                                      AtomicReference<String> newName,
                                      String ext) {
        if (Utils.isEmpty(FileNameUtils.fileName(destination)) && !overwrite) {
            // new generated name is requested
            while (true) {
                IgfsPath newPath = new IgfsPath(newName(destination, ext));
                try {
                    newName.set(newPath.toString());
                    return fs.get().create(newPath, false);
                } catch (IgniteException e) {
                    // intentionally left blank
                }
            }
        } else {
            newName.set(destination.toString());
            return fs.get().create(new IgfsPath(destination), overwrite);
        }
    }

    private String newName(String destination, String ext) {
        return new StringBuilder(destination)
                .append(CvtUtils.toHex(sequence.get().incrementAndGet()))
                .append('.')
                .append(ext)
                .toString();
    }

    @Override
    public boolean exists(ClusterFileSystem fs, String destination) {
        return fs(fs).exists(new IgfsPath(destination));
    }

    private IgniteFileSystem fs(ClusterFileSystem fs) {
        return factory.fs(fs).get();
    }

    @Override
    public boolean remove(ClusterFileSystem fs, String path, boolean recursive) {
        return fs(fs).delete(new IgfsPath(path), recursive);
    }

    @Override
    public long usedSpace(ClusterFileSystem fs, String pathName) {
        IgfsPath path = new IgfsPath(pathName);
        IgfsFile info = fs(fs).info(path);
        
        if (info == null) {
            return 0L;
        }
        return fs(fs).summary(path).totalLength();
    }
}
