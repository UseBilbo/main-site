package com.usebilbo.vertx.module.configurator;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.vertx.annotation.HttpOptionConfiguration;
import com.usebilbo.vertx.configuration.Configurator;
import com.usebilbo.vertx.properties.PropertyContainer;

import io.vertx.core.http.ClientAuth;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpVersion;

@HttpOptionConfiguration
//TODO: add missing properties from NetworkOptions and TCPSSLOptions 
public class HttpOptionsConfigurator implements Configurator<HttpServerOptions> {
    private static final Logger LOG = LogManager.getLogger();

    private final boolean compressionSupported;
    private final int compressionLevel;
    private final int maxWebsocketFrameSize;
    private final int maxWebsocketMessageSize;
    private final String websocketSubProtocols;
    private final boolean handle100ContinueAutomatically;
    private final int maxChunkSize;
    private final int maxInitialLineLength;
    private final int maxHeaderSize;
    private final List<HttpVersion> alpnVersions;
    private final int http2ConnectionWindowSize;
    private final boolean decompressionSupported;
    private final boolean acceptUnmaskedFrames;

    private final int port;
    private final int acceptBacklog;
    private final String host;
    private final ClientAuth clientAuth;

    @Inject
    public HttpOptionsConfigurator(
            @Named("vertx.http.server.compressionSupported") PropertyContainer compressionSupported,
            @Named("vertx.http.server.compressionLevel") PropertyContainer compressionLevel,
            @Named("vertx.http.server.maxWebsocketFrameSize") PropertyContainer maxWebsocketFrameSize,
            @Named("vertx.http.server.maxWebsocketMessageSize") PropertyContainer maxWebsocketMessageSize,
            @Named("vertx.http.server.websocketSubProtocols") PropertyContainer websocketSubProtocols,
            @Named("vertx.http.server.handle100ContinueAutomatically") PropertyContainer handle100ContinueAutomatically,
            @Named("vertx.http.server.maxChunkSize") PropertyContainer maxChunkSize,
            @Named("vertx.http.server.maxInitialLineLength") PropertyContainer maxInitialLineLength,
            @Named("vertx.http.server.maxHeaderSize") PropertyContainer maxHeaderSize,
            @Named("vertx.http.server.alpnVersions") PropertyContainer alpnVersions,
            @Named("vertx.http.server.http2ConnectionWindowSize") PropertyContainer http2ConnectionWindowSize,
            @Named("vertx.http.server.decompressionSupported") PropertyContainer decompressionSupported,
            @Named("vertx.http.server.acceptUnmaskedFrames") PropertyContainer acceptUnmaskedFrames,

            @Named("vertx.http.server.port") PropertyContainer port,
            @Named("vertx.http.server.acceptBacklog") PropertyContainer acceptBacklog,
            @Named("vertx.http.server.host") PropertyContainer host,
            @Named("vertx.http.server.clientAuth") PropertyContainer clientAuth) {

        this.compressionSupported = compressionSupported.asBool(HttpServerOptions.DEFAULT_COMPRESSION_SUPPORTED);
        this.compressionLevel = compressionLevel.asInt(HttpServerOptions.DEFAULT_COMPRESSION_LEVEL);
        this.maxWebsocketFrameSize = maxWebsocketFrameSize.asInt(HttpServerOptions.DEFAULT_MAX_WEBSOCKET_FRAME_SIZE);
        this.maxWebsocketMessageSize = maxWebsocketMessageSize
                .asInt(HttpServerOptions.DEFAULT_MAX_WEBSOCKET_MESSAGE_SIZE);
        this.handle100ContinueAutomatically = handle100ContinueAutomatically
                .asBool(HttpServerOptions.DEFAULT_HANDLE_100_CONTINE_AUTOMATICALLY);
        this.maxChunkSize = maxChunkSize.asInt(HttpServerOptions.DEFAULT_MAX_CHUNK_SIZE);
        this.maxInitialLineLength = maxInitialLineLength.asInt(HttpServerOptions.DEFAULT_MAX_INITIAL_LINE_LENGTH);
        this.maxHeaderSize = maxHeaderSize.asInt(HttpServerOptions.DEFAULT_MAX_HEADER_SIZE);
        this.http2ConnectionWindowSize = http2ConnectionWindowSize
                .asInt(HttpServerOptions.DEFAULT_HTTP2_CONNECTION_WINDOW_SIZE);
        this.decompressionSupported = decompressionSupported.asBool(HttpServerOptions.DEFAULT_DECOMPRESSION_SUPPORTED);
        this.acceptUnmaskedFrames = acceptUnmaskedFrames.asBool(HttpServerOptions.DEFAULT_ACCEPT_UNMASKED_FRAMES);
        this.websocketSubProtocols = websocketSubProtocols.asString();
        this.alpnVersions = toVersions(alpnVersions.split(","));

        this.port = port.asInt(HttpServerOptions.DEFAULT_PORT);
        this.acceptBacklog = acceptBacklog.asInt(HttpServerOptions.DEFAULT_ACCEPT_BACKLOG);
        this.host = host.asString(HttpServerOptions.DEFAULT_HOST);
        this.clientAuth = clientAuth.as(ClientAuth.class, ClientAuth.NONE);
    }

    private List<HttpVersion> toVersions(List<String> versions) {
        return versions.stream().map(s -> toEnum(s)).filter(v -> v != null).collect(Collectors.toList());
    }

    private HttpVersion toEnum(String s) {
        try {
            return Enum.valueOf(HttpVersion.class, s.toUpperCase(Locale.US));
        } catch (IllegalArgumentException e) {
            LOG.warn("Unable to parse value {} of type {}", s, HttpVersion.class.getSimpleName());
        }
        return null;
    }

    @Override
    public void configure(HttpServerOptions options) {
        options.setCompressionSupported(compressionSupported);
        options.setCompressionLevel(compressionLevel);
        options.setMaxWebsocketFrameSize(maxWebsocketFrameSize);
        options.setMaxWebsocketMessageSize(maxWebsocketMessageSize);
        options.setHandle100ContinueAutomatically(handle100ContinueAutomatically);
        options.setMaxChunkSize(maxChunkSize);
        options.setMaxInitialLineLength(maxInitialLineLength);
        options.setMaxHeaderSize(maxHeaderSize);
        options.setHttp2ConnectionWindowSize(http2ConnectionWindowSize);
        options.setDecompressionSupported(decompressionSupported);
        options.setAcceptUnmaskedFrames(acceptUnmaskedFrames);
        options.setWebsocketSubProtocols(websocketSubProtocols);
        options.setAlpnVersions(alpnVersions);
        
        options.setPort(port);
        options.setAcceptBacklog(acceptBacklog);
        options.setHost(host);
        options.setClientAuth(clientAuth);
    }
}
