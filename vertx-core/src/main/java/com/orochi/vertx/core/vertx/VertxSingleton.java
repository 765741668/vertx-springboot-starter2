package com.orochi.vertx.core.vertx;

import io.vertx.core.Vertx;

import java.util.Objects;

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
 *@Description: vertx单例
 *
 *@author  Orochi-Yzh
 *@dateTime  1/4/2020 3:36 PM
*/
public final class VertxSingleton {

    private VertxSingleton() {
    }

    private static class VertxHolder {
        private static Vertx instance;
    }

    public static void init(Vertx vertx) {
        Objects.requireNonNull(vertx, "未初始化Vertx");
        VertxHolder.instance = vertx;
    }

    public static Vertx getInstance() {
        Objects.requireNonNull(VertxHolder.instance, "未初始化Vertx");
        return VertxHolder.instance;
    }

}
