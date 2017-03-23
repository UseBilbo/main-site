package com.usebilbo.vertx.module.configurator;

import static io.vertx.core.net.NetworkOptions.DEFAULT_LOG_ENABLED;
import static io.vertx.core.net.NetworkOptions.DEFAULT_RECEIVE_BUFFER_SIZE;
import static io.vertx.core.net.NetworkOptions.DEFAULT_REUSE_ADDRESS;
import static io.vertx.core.net.NetworkOptions.DEFAULT_SEND_BUFFER_SIZE;
import static io.vertx.core.net.NetworkOptions.DEFAULT_TRAFFIC_CLASS;
import static io.vertx.core.net.TCPSSLOptions.DEFAULT_IDLE_TIMEOUT;
import static io.vertx.core.net.TCPSSLOptions.DEFAULT_SO_LINGER;
import static io.vertx.core.net.TCPSSLOptions.DEFAULT_SSL;
import static io.vertx.core.net.TCPSSLOptions.DEFAULT_TCP_KEEP_ALIVE;
import static io.vertx.core.net.TCPSSLOptions.DEFAULT_TCP_NO_DELAY;
import static io.vertx.core.net.TCPSSLOptions.DEFAULT_USE_ALPN;
import static io.vertx.core.net.TCPSSLOptions.DEFAULT_USE_POOLED_BUFFERS;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.usebilbo.vertx.annotation.HttpOptionConfiguration;
import com.usebilbo.vertx.configuration.Configurator;
import com.usebilbo.vertx.properties.PropertyContainer;

import io.vertx.core.http.HttpServerOptions;

@HttpOptionConfiguration
//TODO: TCPSSLOptions.crlValues is still missing, support it as well?
public class TcpOptionsConfigurator implements Configurator<HttpServerOptions> {
    private final int soLinger;
    private final int idleTimeout;
    private final int sendBufferSize;
    private final int receiveBufferSize;
    private final int trafficClass;
    private final boolean reuseAddress;
    private final boolean logActivity;
    private final boolean tcpNoDelay;
    private final boolean tcpKeepAlive;
    private final boolean usePooledBuffers;
    private final boolean ssl;
    private final boolean useAlpn;
    private final List<String> enabledCipherSuites;
    private final List<String> enabledSecureTransportProtocols;
    private final List<String> crlPaths;

    @Inject
    public TcpOptionsConfigurator(
            @Named("vertx.https.server.soLinger") PropertyContainer soLinger,
            @Named("vertx.https.server.idleTimeout") PropertyContainer idleTimeout,
            @Named("vertx.https.server.sendBufferSize") PropertyContainer sendBufferSize,
            @Named("vertx.https.server.receiveBufferSize") PropertyContainer receiveBufferSize,
            @Named("vertx.https.server.trafficClass") PropertyContainer trafficClass,
            @Named("vertx.https.server.reuseAddress") PropertyContainer reuseAddress,
            @Named("vertx.https.server.logActivity") PropertyContainer logActivity,
            @Named("vertx.https.server.tcpNoDelay") PropertyContainer tcpNoDelay,
            @Named("vertx.https.server.tcpKeepAlive") PropertyContainer tcpKeepAlive,
            @Named("vertx.https.server.usePooledBuffers") PropertyContainer usePooledBuffers,
            @Named("vertx.https.server.ssl") PropertyContainer ssl,
            @Named("vertx.https.server.useAlpn") PropertyContainer useAlpn,
            @Named("vertx.https.server.enabledCipherSuites") PropertyContainer enabledCipherSuites,
            @Named("vertx.https.server.enabledSecureTransportProtocols") PropertyContainer enabledSecureTransportProtocols,
            @Named("vertx.https.server.crlPaths") PropertyContainer crlPaths) {
        this.soLinger = soLinger.asInt(DEFAULT_SO_LINGER);
        this.idleTimeout = idleTimeout.asInt(DEFAULT_IDLE_TIMEOUT);
        this.sendBufferSize = sendBufferSize.asInt(DEFAULT_SEND_BUFFER_SIZE);
        this.receiveBufferSize = receiveBufferSize.asInt(DEFAULT_RECEIVE_BUFFER_SIZE);
        this.trafficClass = trafficClass.asInt(DEFAULT_TRAFFIC_CLASS);
        this.reuseAddress = reuseAddress.asBool(DEFAULT_REUSE_ADDRESS);
        this.logActivity = logActivity.asBool(DEFAULT_LOG_ENABLED);
        this.tcpNoDelay = tcpNoDelay.asBool(DEFAULT_TCP_NO_DELAY);
        this.tcpKeepAlive = tcpKeepAlive.asBool(DEFAULT_TCP_KEEP_ALIVE);
        this.usePooledBuffers = usePooledBuffers.asBool(DEFAULT_USE_POOLED_BUFFERS);
        this.ssl = ssl.asBool(DEFAULT_SSL);
        this.useAlpn = useAlpn.asBool(DEFAULT_USE_ALPN);

        this.enabledCipherSuites = enabledCipherSuites.split();
        this.enabledSecureTransportProtocols = enabledSecureTransportProtocols.split();
        this.crlPaths = crlPaths.split();
    }

    @Override
    public void configure(HttpServerOptions options) {
        options.setSoLinger(soLinger);
        options.setIdleTimeout(idleTimeout);
        options.setSendBufferSize(sendBufferSize);
        options.setReceiveBufferSize(receiveBufferSize);
        options.setTrafficClass(trafficClass);
        options.setReuseAddress(reuseAddress);
        options.setLogActivity(logActivity);
        options.setTcpNoDelay(tcpNoDelay);
        options.setTcpKeepAlive(tcpKeepAlive);
        options.setUsePooledBuffers(usePooledBuffers);
        options.setSsl(ssl);
        options.setUseAlpn(useAlpn);

        enabledCipherSuites.forEach(s -> options.addEnabledCipherSuite(s));
        enabledSecureTransportProtocols.forEach(s -> options.addEnabledSecureTransportProtocol(s));
        crlPaths.forEach(s -> options.addCrlPath(s));
    }
}
