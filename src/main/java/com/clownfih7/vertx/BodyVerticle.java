package com.clownfih7.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @author You
 * @create 2020-10-04 1:22
 */
public class BodyVerticle extends AbstractVerticle {

  // 1. 声明 Router
  private Router router;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    // 2. 初始化 Router
    this.router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    // form-data
    router.post("/form-data").handler(req -> {
      // get param
      String name = req.request().getParam("name");
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x with name=" + name);
    });
    // form-data application/x-www-form-urlencode
    router.post("/x-www-form-urlencode").handler(req -> {
      // get param
      String name = req.request().getFormAttribute("name");
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x with name=" + name);
    });
    // application/json
    router.post("/json").handler(req -> {
      // get json body
      JsonObject body = req.getBodyAsJson();
      req.response()
        .putHeader("content-type", "application/json")
        .end(body.toString());
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
