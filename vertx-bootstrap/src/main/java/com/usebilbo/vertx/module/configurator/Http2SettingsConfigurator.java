package com.usebilbo.vertx.module.configurator;

import javax.inject.Inject;
import javax.inject.Named;

import com.usebilbo.vertx.annotation.HttpOptionConfiguration;
import com.usebilbo.vertx.configuration.Configurator;
import com.usebilbo.vertx.properties.PropertyContainer;

import io.vertx.core.http.Http2Settings;
import io.vertx.core.http.HttpServerOptions;

@HttpOptionConfiguration
public class Http2SettingsConfigurator implements Configurator<HttpServerOptions> {
    private final boolean pushEnabled;
    private final int initialWindowSize;
    private final int maxFrameSize;
    private final long headerTableSize;
    private final long maxConcurrentStreams;
    private final long maxHeaderListSize;

    @Inject
    public Http2SettingsConfigurator(
            @Named("vertx.http2.server.pushEnabled") PropertyContainer pushEnabled,
            @Named("vertx.http2.server.initialWindowSize") PropertyContainer initialWindowSize,
            @Named("vertx.http2.server.maxFrameSize") PropertyContainer maxFrameSize,
            @Named("vertx.http2.server.headerTableSize") PropertyContainer headerTableSize,
            @Named("vertx.http2.server.maxConcurrentStreams") PropertyContainer maxConcurrentStreams,
            @Named("vertx.http2.server.maxHeaderListSize") PropertyContainer maxHeaderListSize) {
     
        this.pushEnabled          = pushEnabled          .asBool(Http2Settings.DEFAULT_ENABLE_PUSH);
        this.initialWindowSize    = initialWindowSize    .asInt (Http2Settings.DEFAULT_INITIAL_WINDOW_SIZE);
        this.maxFrameSize         = maxFrameSize         .asInt (Http2Settings.DEFAULT_MAX_FRAME_SIZE);
        this.headerTableSize      = headerTableSize      .asLong(Http2Settings.DEFAULT_HEADER_TABLE_SIZE);
        this.maxConcurrentStreams = maxConcurrentStreams .asLong(HttpServerOptions.DEFAULT_INITIAL_SETTINGS_MAX_CONCURRENT_STREAMS);
        this.maxHeaderListSize    = maxHeaderListSize    .asLong(Http2Settings.DEFAULT_HEADER_TABLE_SIZE);
    }

    @Override
    public void configure(HttpServerOptions options) {
        Http2Settings settings = new Http2Settings();
        settings.setPushEnabled         (pushEnabled         );
        settings.setInitialWindowSize   (initialWindowSize   );
        settings.setMaxFrameSize        (maxFrameSize        );
        settings.setHeaderTableSize     (headerTableSize     );
        settings.setMaxConcurrentStreams(maxConcurrentStreams);
        settings.setMaxHeaderListSize   (maxHeaderListSize   );
        
        options.setInitialSettings(settings);
    }
    
}
