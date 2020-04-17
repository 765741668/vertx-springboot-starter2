package com.yl.promethues.n.endpoint.listener;

import com.netflix.hystrix.contrib.rxnetty.metricsstream.HystrixMetricsStreamHandler;
import com.soundcloud.prometheus.hystrix.HystrixPrometheusMetricsPublisher;
import com.yl.promethues.n.endpoint.handler.MonitorHandler;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "promethues.hystrix-metrics.dump-engine.enable", havingValue = "true")
@Slf4j
public class DeployVertxListener {
    /**
     * controller api所在包路径
     */
    @Value("${promethues-endpoint.api-packages:com.yl.promethues.n.endpoint.controller}")
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
    public void deployVerticles(ApplicationReadyEvent event) {
        //注册promethues指标数据发布器
        HystrixPrometheusMetricsPublisher.register();
        RxNetty.createHttpServer(httpServerPort, new HystrixMetricsStreamHandler<>(new MonitorHandler())).start();
    }
}
