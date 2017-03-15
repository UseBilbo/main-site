package com.usebilbo.vertx.web.configuration;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.google.common.base.Charsets;
import com.usebilbo.vertx.annotation.RouterConfiguration;
import com.usebilbo.vertx.configuration.Configurator;
import com.usebilbo.vertx.properties.PropertyContainer;
import com.usebilbo.vertx.util.Utils;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

@Singleton
@RouterConfiguration
public class StaticRouteConfigurator implements Configurator<Router> {
    private final boolean allowRootFileSystemAccess;
    private final boolean readOnly;
    private final boolean cachingEnabled;
    private final boolean directoryListing;
    private final boolean includeHidden;
    private final boolean alwaysAsyncFS;
    private final boolean enableFSTuning;
    private final boolean enableRangeSupport;
    private final boolean sendVaryHeader;
    private final long maxAgeSeconds;
    private final long cacheEntryTimeout;
    private final long maxAvgServeTimeNanoSeconds;
    private final int maxCacheSize;
    private final String webRoot;
    private final String indexPage;
    private final String directoryTemplate;
    private final String contentEncoding;
    private final String staticContentRoot;

    @Inject
    public StaticRouteConfigurator(
            @Named("vertx.http.static.staticContentRoot") PropertyContainer staticContentRoot,
            @Named("vertx.http.static.allowRootFileSystemAccess") PropertyContainer allowRootFileSystemAccess,
            @Named("vertx.http.static.readOnly") PropertyContainer readOnly,
            @Named("vertx.http.static.cachingEnabled") PropertyContainer cachingEnabled,
            @Named("vertx.http.static.directoryListing") PropertyContainer directoryListing,
            @Named("vertx.http.static.includeHidden") PropertyContainer includeHidden,
            @Named("vertx.http.static.alwaysAsyncFS") PropertyContainer alwaysAsyncFS,
            @Named("vertx.http.static.enableFSTuning") PropertyContainer enableFSTuning,
            @Named("vertx.http.static.enableRangeSupport") PropertyContainer enableRangeSupport,
            @Named("vertx.http.static.sendVaryHeader") PropertyContainer sendVaryHeader,
            @Named("vertx.http.static.maxAgeSeconds") PropertyContainer maxAgeSeconds,
            @Named("vertx.http.static.cacheEntryTimeout") PropertyContainer cacheEntryTimeout,
            @Named("vertx.http.static.maxAvgServeTimeNanoSeconds") PropertyContainer maxAvgServeTimeNanoSeconds,
            @Named("vertx.http.static.maxCacheSize") PropertyContainer maxCacheSize,
            @Named("vertx.http.static.webRoot") PropertyContainer webRoot,
            @Named("vertx.http.static.indexPage") PropertyContainer indexPage,
            @Named("vertx.http.static.directoryTemplate") PropertyContainer directoryTemplate,
            @Named("vertx.http.static.contentEncoding") PropertyContainer contentEncoding) {

        this.staticContentRoot = staticContentRoot.asString();
        this.allowRootFileSystemAccess = allowRootFileSystemAccess.asBool(StaticHandler.DEFAULT_ROOT_FILESYSTEM_ACCESS);
        this.readOnly = readOnly.asBool(StaticHandler.DEFAULT_FILES_READ_ONLY);
        this.cachingEnabled = cachingEnabled.asBool(StaticHandler.DEFAULT_CACHING_ENABLED);
        this.directoryListing = directoryListing.asBool(StaticHandler.DEFAULT_DIRECTORY_LISTING);
        this.includeHidden = includeHidden.asBool(StaticHandler.DEFAULT_INCLUDE_HIDDEN);
        this.alwaysAsyncFS = alwaysAsyncFS.asBool(StaticHandler.DEFAULT_ALWAYS_ASYNC_FS);
        this.enableFSTuning = enableFSTuning.asBool(StaticHandler.DEFAULT_ENABLE_FS_TUNING);
        this.enableRangeSupport = enableRangeSupport.asBool(StaticHandler.DEFAULT_RANGE_SUPPORT);
        this.sendVaryHeader = sendVaryHeader.asBool(StaticHandler.DEFAULT_SEND_VARY_HEADER);
        this.maxAgeSeconds = maxAgeSeconds.asDuration(StaticHandler.DEFAULT_MAX_AGE_SECONDS * 1000) / 1000;
        this.cacheEntryTimeout = cacheEntryTimeout.asDuration(StaticHandler.DEFAULT_CACHE_ENTRY_TIMEOUT);
        this.maxAvgServeTimeNanoSeconds = maxAvgServeTimeNanoSeconds
                .asDuration(StaticHandler.DEFAULT_MAX_AVG_SERVE_TIME_NS / 1000) * 1000;
        this.maxCacheSize = maxCacheSize.asInt(StaticHandler.DEFAULT_MAX_CACHE_SIZE);
        this.webRoot = webRoot.asString(StaticHandler.DEFAULT_WEB_ROOT);
        this.indexPage = indexPage.asString(StaticHandler.DEFAULT_INDEX_PAGE);
        this.directoryTemplate = directoryTemplate.asString();
        this.contentEncoding = contentEncoding.asString(Charsets.UTF_8.name());
    }

    @Override
    public void configure(Router router) {
        StaticHandler staticHandler = StaticHandler.create()
                .setAllowRootFileSystemAccess(allowRootFileSystemAccess)
                .setFilesReadOnly(readOnly)
                .setCachingEnabled(cachingEnabled)
                .setDirectoryListing(directoryListing)
                .setIncludeHidden(includeHidden)
                .setAlwaysAsyncFS(alwaysAsyncFS)
                .setEnableFSTuning(enableFSTuning)
                .setEnableRangeSupport(enableRangeSupport)
                .setSendVaryHeader(sendVaryHeader)
                .setMaxAgeSeconds(maxAgeSeconds)
                .setCacheEntryTimeout(cacheEntryTimeout)
                .setMaxAvgServeTimeNs(maxAvgServeTimeNanoSeconds)
                .setMaxCacheSize(maxCacheSize)
                .setWebRoot(webRoot)
                .setIndexPage(indexPage)
                .setDirectoryTemplate(directoryTemplate)
                .setDefaultContentEncoding(contentEncoding);

        if (!Utils.isEmpty(staticContentRoot)) {
            router.route(staticContentRoot).handler(staticHandler);

        } else {
            router.route().handler(staticHandler);
        }
    }
}
