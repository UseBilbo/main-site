package com.usebilbo.vertx.util;

import static com.usebilbo.vertx.util.SetUtils.asSet;
import static com.usebilbo.vertx.util.SetUtils.merge;
import static com.usebilbo.vertx.util.SetUtils.setOf;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public class SetUtilsTest {
    
    @Test
    public void setOfReturnsSetWithSpecifiedElementsPresent() throws Exception {
        assertThat(setOf(DeploymentMode.class, new DeploymentMode[] {DeploymentMode.DEV})).containsOnly(DeploymentMode.DEV);
    }
    
    @Test
    public void mergeReturnsUnion() throws Exception {
        Set<String> s1 = asSet("v1", "v2", "v3");
        Set<String> s2 = asSet("v4", "v5", "v5");
        
        assertThat(merge(s1, s2)).isEqualTo(asSet("v1", "v2", "v3", "v4", "v5"));
    }

    @Test
    public void mergeSquezesIdenticalElements() throws Exception {
        Set<String> s1 = asSet("v1", "v2", "v3", "v4");
        Set<String> s2 = asSet("v1", "v4", "v5", "v5");
        
        assertThat(merge(s1, s2)).isEqualTo(asSet("v1", "v2", "v3", "v4", "v5"));
    }

    @Test
    public void mergeWithEmptyReturnsIdenticalSet() throws Exception {
        Set<String> s1 = asSet("v1", "v2", "v3", "v4");
        Set<String> s2 = Collections.emptySet();
        
        assertThat(merge(s1, s2)).isEqualTo(asSet("v1", "v2", "v3", "v4"));
        assertThat(merge(s2, s1)).isEqualTo(asSet("v1", "v2", "v3", "v4"));
    }

    @Test
    public void mergeWithNullReturnsIdenticalSet() throws Exception {
        Set<String> s1 = asSet("v1", "v2", "v3", "v4");
        Set<String> s2 = null;
        
        assertThat(merge(s1, s2)).isEqualTo(asSet("v1", "v2", "v3", "v4"));
        assertThat(merge(s2, s1)).isEqualTo(asSet("v1", "v2", "v3", "v4"));
    }
}
