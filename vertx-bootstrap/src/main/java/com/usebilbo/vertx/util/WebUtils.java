package com.usebilbo.vertx.util;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;

public final class WebUtils {
    private WebUtils() {}
    
    //TODO: finish it
    public static void sendError(String message, HttpResponseStatus status) {
        
    }
    
    public static void sendRedirect(HttpServerResponse response, String destination) {
        response.setStatusCode(HttpResponseStatus.FOUND.code());
        response.putHeader(HttpHeaders.LOCATION.toString(), destination);
        response.end();
    }
}
