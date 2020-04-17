package com.yl.promethues.v.endpoint.metrics;

import com.yl.hystrix.dump.HystrixDumpMetrics;
import com.yl.hystrix.dump.enums.HystrixFormatType;
import com.yl.promethues.v.endpoint.util.DumpHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public final class PromethuesHystrixDumpMetricsFactory implements HystrixDumpMetrics {

    @Override
    public Consumer<HystrixFormatType> useFormat() {
        return this::initFormat;
    }

    private void initFormat(HystrixFormatType formatType){
        log.info("初始化采集平台格式: {}", formatType.name());
        DumpHelper.FORMAT = formatType;
    }

}