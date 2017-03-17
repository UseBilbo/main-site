package com.usebilbo.vertx.web.configuration;

import java.util.List;

public interface RestBean {
    String path();
    
    List<RestMethod> methods();
    
    Class<?> type();
    
    String methodPath(String path);
}
