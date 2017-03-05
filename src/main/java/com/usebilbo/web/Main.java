package com.usebilbo.web;

import com.usebilbo.util.Runner;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class Main extends AbstractVerticle {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        //TODO: enable caching for prod
        System.setProperty("vertx.disableFileCaching", "true");
        System.setProperty("java.util.logging.config.file", "logging.properties");

        Runner.run(Main.class);
    }

    @Override
    public void start() {
        Router router = Router.router(vertx);

        // Serve the static pages
        StaticHandler staticHandler = StaticHandler.create()
                .setCachingEnabled(false)
                .setFilesReadOnly(false)
                .setEnableRangeSupport(true)
                .setAlwaysAsyncFS(false)
                .setEnableFSTuning(false)
                .setWebRoot("landing-page");

        router.route().handler(staticHandler); // TODO: try to specify root

        vertx.createHttpServer().requestHandler(router::accept).listen(PORT);

        System.out.println("Server is started at " + PORT);

    }
}
