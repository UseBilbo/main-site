package com.usebilbo.vertx.util;

import static org.assertj.core.api.Assertions.assertThat;
import static com.usebilbo.vertx.util.FileNameUtils.extension;
import static com.usebilbo.vertx.util.FileNameUtils.fileName;
import static com.usebilbo.vertx.util.FileNameUtils.sanitizeRoot;

import org.junit.Test;

public class FileNameUtilsTest {
    
    @Test
    public void extensionIsExtractedCorrectly() throws Exception {
        assertThat(extension("/index.html")).isEqualTo("html");
        assertThat(extension("/path.ext/index.js")).isEqualTo("js");
        assertThat(extension("/path.ext/index")).isEqualTo("");
        assertThat(extension(null)).isEqualTo("");
        assertThat(extension("")).isEqualTo("");
    }

    @Test
    public void fileNameIsExtractedCorrectly() throws Exception {
        assertThat(fileName(null)).isEqualTo("");
        assertThat(fileName("file.txt")).isEqualTo("file.txt");
        assertThat(fileName("/")).isEqualTo("");
        assertThat(fileName("/path/file.txt")).isEqualTo("file.txt");
        assertThat(fileName("../.../../file.txt")).isEqualTo("file.txt");
        assertThat(fileName("\\path\\file.txt")).isEqualTo("file.txt");
        assertThat(fileName("C:\\path\\file.txt")).isEqualTo("file.txt");
        assertThat(fileName("..\\...\\..\\file.txt")).isEqualTo("file.txt");
    }
    
    @Test
    public void sanitizeRootIsWorkingProperly() throws Exception {
        assertThat(sanitizeRoot(null)).isNull();
        assertThat(sanitizeRoot("")).isEqualTo("");
        assertThat(sanitizeRoot("/")).isEqualTo("/");
        assertThat(sanitizeRoot("a")).isEqualTo("a/");
        assertThat(sanitizeRoot("/a")).isEqualTo("/a/");
    }
}
