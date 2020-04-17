package com.yl.promethues.n.endpoint.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import rx.Observable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MonitorHandler implements RequestHandler<ByteBuf, ByteBuf> {

    private static final String METRICS_PATH = "/hystrix/metrics";

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
        if (request.getPath().startsWith(METRICS_PATH)) {
            ByteBuf buffer = Unpooled.buffer();
            try (BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new ByteBufOutputStream(buffer)))) {
                CollectorRegistry defaultRegistry = CollectorRegistry.defaultRegistry;
                TextFormat.write004(bufWriter, defaultRegistry.metricFamilySamples());
            } catch (IOException e) {
                response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
                return response.writeStringAndFlush("ERROR");
            }

            response.setStatus(HttpResponseStatus.OK);
            response.getHeaders().add(HttpHeaderNames.CONTENT_TYPE, TextFormat.CONTENT_TYPE_004);
            return response.writeAndFlush(buffer);
        }
        response.setStatus(HttpResponseStatus.BAD_REQUEST);
        return response.writeStringAndFlush("BAD REQUEST");
    }
}