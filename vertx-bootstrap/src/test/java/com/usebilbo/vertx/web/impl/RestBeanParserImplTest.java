package com.usebilbo.vertx.web.impl;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

//TODO: more tests
public class RestBeanParserImplTest {
    @Test
    public void pathIsNormalizedProperly() throws Exception {
        assertThat(RestBeanParserImpl.normalizePath(null)).isEqualTo("/");
        assertThat(RestBeanParserImpl.normalizePath("")).isEqualTo("/");
        assertThat(RestBeanParserImpl.normalizePath("/")).isEqualTo("/");
        assertThat(RestBeanParserImpl.normalizePath(" /")).isEqualTo("/");
        assertThat(RestBeanParserImpl.normalizePath(" / ")).isEqualTo("/");
        assertThat(RestBeanParserImpl.normalizePath(" / /")).isEqualTo("/");
        assertThat(RestBeanParserImpl.normalizePath("/path")).isEqualTo("/path");
        assertThat(RestBeanParserImpl.normalizePath("path")).isEqualTo("/path");
        assertThat(RestBeanParserImpl.normalizePath("/path/")).isEqualTo("/path");
        assertThat(RestBeanParserImpl.normalizePath("path/")).isEqualTo("/path");
        assertThat(RestBeanParserImpl.normalizePath("more/complex/path/")).isEqualTo("/more/complex/path");
        assertThat(RestBeanParserImpl.normalizePath("more/complex/path /")).isEqualTo("/more/complex/path");
    }
}
