package com.clownfih7.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

/**
 * @author You
 * @create 2020-10-04 1:22
 */
public class UrlParamsVerticle extends AbstractVerticle {

  // 1. 声明 Router
  private Router router;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    // 2. 初始化 Router
    this.router = Router.router(vertx);
    // http://127.0.0.1:8888/test?name=xxx
    router.get("/test").handler(req -> {
      // url param
      String name = req.request().getParam("name");
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x with name=" + name);
    });
    // http://127.0.0.1:8888/test/{name}
    router.get("/test/:name").handler(req -> {
      // rest param
      String name = req.request().getParam("name");
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x with name=" + name);
    });
    // 3. 将 Router 与 vertx HttpServer 绑定
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8888, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP server started on port 8888");
        } else {
          startPromise.fail(http.cause());
        }
      });
  }
}
