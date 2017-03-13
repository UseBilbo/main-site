package com.usebilbo.vertx.module;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

//TODO: replace with real test
public class PropertiesModuleTest {
    @Test
    public void testName() throws Exception {
        System.out.println(calculatePrefix());
    }

    private String calculatePrefix() {
        String[] parts = PropertiesModule.class.getPackage().getName().split("\\.", 3);
        return new StringBuilder(parts[0]).append('/').append(parts[1]).toString();
    }
}
