package com.usebilbo.vertx.util.testbeans;

import javax.inject.Named;

import com.usebilbo.vertx.annotation.Description;
import com.usebilbo.vertx.annotation.Mandatory;

@Named("BeanTest1")
@Description("description for TestBean1")
public class TestBean1 {
    @Mandatory
    @Named("field42")
    private String field1;
}
