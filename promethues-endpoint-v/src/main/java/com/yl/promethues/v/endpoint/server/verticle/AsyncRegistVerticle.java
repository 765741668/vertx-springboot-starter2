package com.yl.promethues.v.endpoint.server.verticle;

import com.yl.promethues.v.endpoint.server.anno.AsyncServiceHandler;
import com.yl.promethues.v.endpoint.server.utils.ReflectionUtil;
import com.yl.promethues.v.endpoint.server.utils.SpringContextUtil;
import com.yl.promethues.v.endpoint.server.vertx.VertxSingleton;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
 *@Description: 服务注册到EventBus
 *
 *@author  Orochi-Yzh
 *@dateTime  15/4/2020 15:15
*/
@Slf4j
public class AsyncRegistVerticle extends AbstractVerticle {

    private String packageAddress;

    public AsyncRegistVerticle(String packageAddress) {
        Objects.requireNonNull(packageAddress, "given scan package address is empty");
        this.packageAddress = packageAddress;
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Set<Class<?>> handlers = ReflectionUtil.getReflections(packageAddress).getTypesAnnotatedWith(AsyncServiceHandler.class);
        ServiceBinder binder = new ServiceBinder(VertxSingleton.getInstance());
        if (null != handlers && handlers.size() > 0) {
            List<Future> ftList = new ArrayList<>();
            handlers.forEach(asyncService -> {
                Future ft = Future.future();
                try {
                    Object asInstance = SpringContextUtil.getBean(asyncService);
                    Method getAddressMethod = asyncService.getMethod("getAddress");
                    String address = (String) getAddressMethod.invoke(asInstance);
                    Method getAsyncInterfaceClassMethod = asyncService.getMethod("getAsyncInterfaceClass");
                    Class clazz = (Class) getAsyncInterfaceClassMethod.invoke(asInstance);
                    binder.setAddress(address).register(clazz, asInstance).completionHandler(ft);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ftList.add(ft);
            });
            CompositeFuture.all(ftList).setHandler(ar -> {
                if (ar.succeeded()) {
                    log.info("All async services registered");
                    startPromise.complete();
                } else {
                    log.error(ar.cause().getMessage());
                    startPromise.fail(ar.cause());
                }
            });
        }
    }
}
