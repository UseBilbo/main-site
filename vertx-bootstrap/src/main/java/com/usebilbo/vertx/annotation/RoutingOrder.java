package com.usebilbo.vertx.annotation;

public enum RoutingOrder {
    FIRST,
    BEFORE_BODY_PARSED,
    BODY_PARSER,
    AFTER_BODY_PARSED,
    MIDDLE,
    LAST
}
