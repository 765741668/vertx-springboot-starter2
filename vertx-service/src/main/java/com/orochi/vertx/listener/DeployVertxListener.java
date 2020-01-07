package com.orochi.vertx.listener;

import com.orochi.vertx.core.handlerfactory.RouterHandlerFactory;
import com.orochi.vertx.core.vertx.DeployVertxServer;
import com.orochi.vertx.core.vertx.VertxSingleton;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.ext.web.Router;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DeployVertxListener {
    /**
     * controller api所在包路径
     */
    @Value("${controller-api-packages:com.orochi.vertx.controller}")
    private String webApiPackages;

    /**
     * 异步服务所在包路径
     */
    @Value("${async-service-impl-packages:com.orochi.vertx.service.impl,com.orochi.vertx.service2.impl}")
    private String asyncServiceImplPackages;

    /**
     * http服务器端口号
     */
    @Value("${http-server-port:8989}")
    private int httpServerPort;

    /**
     * 工作线程池大小（可根据实际情况调整）
     */
    @Value("${worker-pool-size:100}")
    private int workerPoolSize;

    /**
     * 异步服务实例数量（建议和CPU核数相同）
     */
    @Value("${async-service-instances:1}")
    private int asyncServiceInstances;

    @Value("${event-bus-connect-timeout:10000}")
    private int eventBusOptionsConnectTimeout;

    @EventListener
    public void deployVerticles(ApplicationReadyEvent event) {
        EventBusOptions eventBusOptions = new EventBusOptions();
        //便于调试 设定超时等时间较长 生产环境建议适当调整
        eventBusOptions.setConnectTimeout(eventBusOptionsConnectTimeout);
        Vertx vertx = Vertx.vertx(
                new VertxOptions().setWorkerPoolSize(workerPoolSize)
                                  .setEventBusOptions(eventBusOptions)
                                  .setBlockedThreadCheckInterval(999999999L)
                                  .setMaxEventLoopExecuteTime(Long.MAX_VALUE)
        );

//        Vertx vertx = Vertx.vertx();
        VertxSingleton.init(vertx);
        try {
            Router router = new RouterHandlerFactory(webApiPackages).createRouter();
            DeployVertxServer.startDeploy(router, asyncServiceImplPackages, httpServerPort, asyncServiceInstances);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
