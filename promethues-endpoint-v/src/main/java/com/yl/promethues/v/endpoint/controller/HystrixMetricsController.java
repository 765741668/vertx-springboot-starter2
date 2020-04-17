package com.yl.promethues.v.endpoint.controller;

import com.yl.promethues.v.endpoint.server.anno.RouteHandler;
import com.yl.promethues.v.endpoint.server.anno.RouteMapping;
import com.yl.promethues.v.endpoint.server.anno.RouteMethod;
import com.yl.promethues.v.endpoint.util.DumpHelper;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.prometheus.client.exporter.common.TextFormat;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Value;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_
 * //                         o8888888o
 * //                         88" . "88
 * //                         (| ^_^ |)
 * //                         O\  =  /O
 * //                      ____/`---'\____
 * //                    .'  \\|     |//  `.
 * //                   /  \\|||  :  |||//  \
 * //                  /  _||||| -:- |||||-  \
 * //                  |   | \\\  -  /// |   |
 * //                  | \_|  ''\---/''  |   |
 * //                  \  .-\__  `-`  ___/-. /
 * //                ___`. .'  /--.--\  `. . ___
 * //              ."" '<  `.___\_<|>_/___.'  >'"".
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /
 * //      ========`-.____`-.___\_____/___.-`____.-'========
 * //                           `=---='
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * //         佛祖保佑       永无BUG     永不修改
 * ////////////////////////////////////////////////////////////////////
 *@Description:
 *
 *@author  Orochi-Yzh
 *@dateTime  15/4/2020 16:51
*/
@RouteHandler("hystrix")
public class HystrixMetricsController {

    //不停机关闭配置的时候，不采集数据
//    @Value("promethues.hystrix-metrics.dump-engine.enable:false")
    private boolean enable = true;

    @RouteMapping(value = "/metrics", method = RouteMethod.GET)
    public Handler<RoutingContext> dumpMetrics() {
        return DumpHelper::dumpMetrics;
    }
}
