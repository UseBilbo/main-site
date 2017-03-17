package com.usebilbo.vertx.module;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

//TODO: more tests
public class PropertiesModuleTest {
    @Test
    public void testName() throws Exception {
        assertThat(PropertiesModule.calculatePrefix()).isEqualTo("com/usebilbo");
    }
}
