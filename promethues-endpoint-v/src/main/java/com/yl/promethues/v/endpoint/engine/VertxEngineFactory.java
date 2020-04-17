package com.yl.promethues.v.endpoint.engine;

import com.soundcloud.prometheus.hystrix.HystrixPrometheusMetricsPublisher;
import com.yl.hystrix.dump.CollectEngine;
import com.yl.hystrix.dump.EngineInitializer;
import com.yl.hystrix.dump.enums.EngineType;
import com.yl.promethues.v.endpoint.server.vertx.VertxSingleton;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import lombok.extern.slf4j.Slf4j;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2019<br>
 * *
 *
 * @Description: 类描述
 * @date 2020/4/16 17:44
 * @Author: <a href= "765741668@qq.com">yangzonghua</a>
 */
@Slf4j
public class VertxEngineFactory implements CollectEngine {

    @Override
    public EngineInitializer<String, Integer> useEngine(EngineType engineType) {
        return this::initVerticles;
    }

    public void initVerticles(String webApiPackages, Integer httpServerPort, Integer workerPoolSize, Integer eventBusOptionsConnectTimeout) {
        log.info("初始化采集引擎: Vertx");
        //注册promethues指标数据发布器
        HystrixPrometheusMetricsPublisher.register();
        //启动容器
        EventBusOptions eventBusOptions = new EventBusOptions();
        //便于调试 设定超时等时间较长 生产环境建议适当调整
        eventBusOptions.setConnectTimeout(eventBusOptionsConnectTimeout);
        Vertx vertx = Vertx.vertx(
                //Vert.x实例中使用的Event Loop线程的数量，默认值为：2 * Runtime.getRuntime().availableProcessors()
                //可用的处理器个数 * 2
                new VertxOptions().setEventLoopPoolSize(2 * Runtime.getRuntime().availableProcessors())
                        //Vert.x实例中支持的Worker线程的最大数量
                        .setWorkerPoolSize(workerPoolSize)
                        //内部阻塞线程池最大线程数，这个参数主要被Vert.x的一些内部操作使用，默认值为20
                        .setInternalBlockingPoolSize(20)
                        //阻塞线程检查的时间间隔，默认1000，单位ms，即1秒
                        .setBlockedThreadCheckInterval(1000)
                //Event Loop的最大执行时间，默认2l * 1000 * 1000000，单位ns，即2秒
//                        .setMaxEventLoopExecuteTime(2)
                //Worker线程的最大执行时间，默认60l * 1000 * 1000000，单位ns，即60秒
//                        .setMaxWorkerExecuteTime(60)
                //如果线程阻塞时间超过了这个阀值，那么就会打印警告的堆栈信息，默认为5l * 1000 * 1000000，单位ns，即5秒
//                        .setWarningExceptionTime(5)
        );

        VertxSingleton.init(vertx);
    }
}
