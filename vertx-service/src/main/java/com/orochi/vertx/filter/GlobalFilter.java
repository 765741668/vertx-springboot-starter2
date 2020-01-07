package com.orochi.vertx.filter;

import com.orochi.vertx.core.anno.RouteHandler;
import com.orochi.vertx.core.anno.RouteMapping;
import com.orochi.vertx.core.anno.RouteMethod;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

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
 *@dateTime  1/4/2020 4:23 PM
*/
@RouteHandler(order = 1)
public class GlobalFilter {

    @RouteMapping(value = "*", method = RouteMethod.ROUTE)
    public Handler<RoutingContext> globalFilter() {
        return ctx -> {
//            System.err.println("全局过滤器！");
            ctx.next();
        };
    }
}
