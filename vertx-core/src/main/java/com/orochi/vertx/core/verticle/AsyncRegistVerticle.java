package com.orochi.vertx.core.verticle;

import com.orochi.vertx.core.anno.AsyncServiceHandler;
import com.orochi.vertx.core.utils.ReflectionUtil;
import com.orochi.vertx.core.utils.SpringContextUtil;
import com.orochi.vertx.core.vertx.VertxSingleton;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

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
 *@dateTime  1/4/2020 3:35 PM
*/
public class AsyncRegistVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncRegistVerticle.class);

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
                    LOGGER.info("All async services registered");
                    startPromise.complete();
                } else {
                    LOGGER.error(ar.cause().getMessage());
                    startPromise.fail(ar.cause());
                }
            });
        }
    }
}
