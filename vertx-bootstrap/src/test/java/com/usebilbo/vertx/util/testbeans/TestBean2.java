package com.usebilbo.vertx.util.testbeans;

import com.google.inject.name.Named;
import com.usebilbo.vertx.annotation.Description;
import com.usebilbo.vertx.annotation.Mandatory;

public class TestBean2 extends TestBean1 {
    @Mandatory
    @Named("field66")
    @Description("Description for field2")
    private String field2;
}
