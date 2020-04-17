package com.yl.promethues.v.endpoint.util;

import com.yl.hystrix.dump.enums.HystrixFormatType;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;

import static com.yl.hystrix.dump.enums.HystrixFormatType.PROMETHUES;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2019<br>
 * *
 *
 * @Description: 类描述
 * @date 2020/4/16 20:20
 * @Author: <a href= "765741668@qq.com">yangzonghua</a>
 */
@Slf4j
public final class DumpHelper {

    public static HystrixFormatType FORMAT = PROMETHUES;

    public static void dumpMetrics(RoutingContext ctx) {
        ctx.vertx().<Buffer>executeBlocking(future -> {
            try (final BufferWriter writer = new BufferWriter()) {
                switch (FORMAT){
                    case ZABBIX:
                        String msg = "目前还未支持ZABBIX的Hystrix指标数据采集...";
                        log.warn(msg);
                        ctx.response()
                                .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                                .putHeader("Content-Type", TextFormat.CONTENT_TYPE_004)
                                .end(msg);
                        break;
                    case PROMETHUES:
                    default:
                        CollectorRegistry registry = CollectorRegistry.defaultRegistry;
                        TextFormat.write004(writer, registry.metricFamilySamples());
                        break;
                }
                future.complete(writer.buffer);
            } catch (IOException e) {
                future.fail(e);
            }
        }, false, result -> {
            if (result.succeeded()) {
                ctx.response()
                        .setStatusCode(HttpResponseStatus.OK.code())
                        .putHeader("Content-Type", TextFormat.CONTENT_TYPE_004)
                        .end(result.result());
            } else {
                ctx.fail(result.cause());
            }
        });
    }

    private static final class BufferWriter extends Writer {
        private final Buffer buffer = Buffer.buffer();

        @Override
        public void write(char[] chars, int off, int len) {
            buffer.appendString(new String(chars, off, len));
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }
    }
}
