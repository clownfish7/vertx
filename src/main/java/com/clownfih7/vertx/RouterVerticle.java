package com.clownfih7.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

/**
 * @author You
 * @create 2020-10-04 1:22
 */
public class RouterVerticle extends AbstractVerticle {

  // 1. 声明 Router
  private Router router;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    // 2. 初始化 Router
    this.router = Router.router(vertx);
    router.route("/").handler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    });
    router.get("/get").handler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x router with get!");
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
