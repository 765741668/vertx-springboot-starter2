package com.yl.vertx.server.vertx;

import com.yl.vertx.server.verticle.AsyncRegistVerticle;
import com.yl.vertx.server.verticle.RouterRegistryVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

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
 *@Description: 开始注册vertx相关服务
 *
 *@author  Orochi-Yzh
 *@dateTime  15/4/2020 15:15
*/
@Slf4j
public class DeployVertxServer {

    public static void startDeploy(Router router,  int port) throws IOException {
        log.info("Start Deploy....");
        log.info("Start registry router....");
        VertxSingleton.getInstance().deployVerticle(new RouterRegistryVerticle(router, port));
    }

    public static void startDeploy(Router router, String asyncServiceImplPackages, int port, int asyncServiceInstances) throws IOException {
        log.info("Start Deploy....");
        log.info("Start registry router....");
        VertxSingleton.getInstance().deployVerticle(new RouterRegistryVerticle(router, port));
        log.info("Start registry service....");
        if (asyncServiceInstances < 1) {
            asyncServiceInstances = 1;
        }
        for (int i = 0; i < asyncServiceInstances; i++) {
            VertxSingleton.getInstance().deployVerticle(new AsyncRegistVerticle(asyncServiceImplPackages), new DeploymentOptions().setWorker(true));
        }
    }

    public static void startDeploy(Router router, String asyncServiceImplPackages, int asyncServiceInstances) throws IOException {
        log.info("Start Deploy....");
        log.info("Start registry router....");
        VertxSingleton.getInstance().deployVerticle(new RouterRegistryVerticle(router));
        log.info("Start registry service....");
        if (asyncServiceInstances < 1) {
            asyncServiceInstances = 1;
        }
        for (int i = 0; i < asyncServiceInstances; i++) {
            VertxSingleton.getInstance().deployVerticle(new AsyncRegistVerticle(asyncServiceImplPackages), new DeploymentOptions().setWorker(true));
        }
    }
}
