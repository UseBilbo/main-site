package com.usebilbo.vertx.util;

import static com.usebilbo.vertx.util.Naming.classNames;
import static com.usebilbo.vertx.util.Naming.description;
import static com.usebilbo.vertx.util.Naming.name;
import static com.usebilbo.vertx.util.Naming.names;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

import javax.inject.Named;

import org.junit.Test;

import com.usebilbo.vertx.util.testbeans.TestBean1;
import com.usebilbo.vertx.util.testbeans.TestBean2;

public class NamingTest {

    @Test
    public void nameIsExtractedCorrectlyFromClass() throws Exception {
        assertThat(name(TestBean1.class)).isEqualTo("BeanTest1");
        assertThat(name(TestBean2.class)).isEqualTo("TestBean2");
    }

    @Test
    public void nameIsExtractedCorrectlyFromField() throws Exception {
        assertThat(name(TestBean1.class.getDeclaredField("field1"))).isEqualTo("field42");
        assertThat(name(TestBean2.class.getDeclaredField("field2"))).isEqualTo("field66");
    }

    @Test
    @Named("alternateName")
    public void nameIsExtractedCorrectlyFromMethod() throws Exception {
        assertThat(name(getClass().getMethod("nameIsExtractedCorrectlyFromMethod"))).isEqualTo("alternateName");
    }

    @Test
    public void descriptionIsExtractedCorrectlyFromClass() throws Exception {
        assertThat(description(TestBean1.class)).isEqualTo("description for TestBean1");
        assertThat(description(TestBean2.class)).isNull();
    }

    @Test
    public void descriptionIsExtractedCorrectlyFromField() throws Exception {
        assertThat(description(TestBean1.class.getDeclaredField("field1"))).isNull();
        assertThat(description(TestBean2.class.getDeclaredField("field2"))).isEqualTo("Description for field2");
    }

    @Test
    public void nameListIsBuiltFromClassList() throws Exception {
        assertThat(names(Arrays.asList(Number.class, Integer.class, Long.class))).isEqualTo("Number, Integer, Long");
        assertThat(names(Collections.emptyList())).isEqualTo("");
        assertThat(names(null)).isEqualTo("");
    }

    @Test
    public void classNameListIsBuiltFromListOfInstances() throws Exception {
        assertThat(classNames(Arrays.asList(new Integer(12), new Long(34)))).isEqualTo("Integer, Long");
        assertThat(classNames(Collections.emptyList())).isEqualTo("");
        assertThat(classNames(null)).isEqualTo("");
    }
}
