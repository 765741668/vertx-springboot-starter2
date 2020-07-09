package com.yl.container.v.listener;

import com.yl.vertx.server.engine.CollectEngine;
import com.yl.vertx.server.enums.EngineType;
import com.yl.vertx.server.handlerfactory.RouterHandlerFactory;
import com.yl.vertx.server.vertx.DeployVertxServer;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ServiceLoader;

@Component
@ConditionalOnProperty(value = "promethues.hystrix-metrics.dump-engine.enable", havingValue = "true")
@Slf4j
public class DeployVertxListener {
    /**
     * controller api所在包路径
     */
    @Value("${promethues-endpoint.api-packages:com.yl.promethues.v.endpoint.controller}")
    private String webApiPackages;

    /**
     * http服务器端口号
     */
    @Value("${promethues-endpoint.port:15555}")
    private int httpServerPort;

    /**
     * 工作线程池大小（可根据实际情况调整）
     */
    @Value("${promethues-endpoint.worker-pool-size:20}")
    private int workerPoolSize;

    @Value("${event-bus-connect-timeout:10000}")
    private int eventBusOptionsConnectTimeout;

    @EventListener
    public void startEngine(ApplicationReadyEvent event) {

        //初始化引擎
        ServiceLoader<CollectEngine> engineLoader = ServiceLoader.load(CollectEngine.class);
        for (CollectEngine engine : engineLoader ) {
            engine.useEngine(EngineType.VERTX).init(webApiPackages, httpServerPort, workerPoolSize, eventBusOptionsConnectTimeout);
            break;
        }

        //启动引擎
        try {
            Router router = new RouterHandlerFactory(webApiPackages).createRouter();
            DeployVertxServer.startDeploy(router, httpServerPort);
        } catch (IOException e) {
            log.error("Promethues采集器启动失败：{}", e.getMessage(), e);
        }

    }
}
