package com.yl.promethues.v.endpoint.listener;

import com.soundcloud.prometheus.hystrix.HystrixPrometheusMetricsPublisher;
import com.yl.hystrix.dump.CollectEngine;
import com.yl.hystrix.dump.HystrixDumpMetrics;
import com.yl.hystrix.dump.enums.EngineType;
import com.yl.hystrix.dump.enums.HystrixFormatType;
import com.yl.promethues.v.endpoint.server.handlerfactory.RouterHandlerFactory;
import com.yl.promethues.v.endpoint.server.vertx.DeployVertxServer;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ServiceLoader;

@Slf4j
public class VertxEngine {

    public void startEngine(String portStr) {
        int port = 15555;
        if(StringUtils.isNoneBlank(portStr)){
            port = Integer.parseInt(portStr);
        }
        //注册promethues指标数据发布器
        HystrixPrometheusMetricsPublisher.register();

        //初始化引擎
        ServiceLoader<CollectEngine> engineLoader = ServiceLoader.load(CollectEngine.class);
        String webApiPackages = "com.yl.promethues.v.endpoint.controller";

        for (CollectEngine engine : engineLoader ) {
            int workerPoolSize = 20;
            int eventBusOptionsConnectTimeout = 10000;
            engine.useEngine(EngineType.VERTX).init(webApiPackages, port, workerPoolSize, eventBusOptionsConnectTimeout);
            break;
        }

        //设置采集模板
        ServiceLoader<HystrixDumpMetrics> dumLoader = ServiceLoader.load(HystrixDumpMetrics.class);
        for (HystrixDumpMetrics dumpMetrics : dumLoader ) {
            dumpMetrics.useFormat().accept(HystrixFormatType.PROMETHUES);
            break;
        }

        //启动引擎
        try {
            Router router = new RouterHandlerFactory(webApiPackages).createRouter();
            DeployVertxServer.startDeploy(router, port);
        } catch (IOException e) {
            log.error("Promethues采集器启动失败：{}", e.getMessage(), e);
        }

    }
}
