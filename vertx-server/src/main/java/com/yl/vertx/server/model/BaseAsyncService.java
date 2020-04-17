package com.yl.vertx.server.model;

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
 *@Description: 异步服务基础接口
 *
 *@author  Orochi-Yzh
 *@dateTime  15/4/2020 15:15
*/
public interface BaseAsyncService {

    default String getAddress() {
        String className = this.getClass().getName();
        return className.substring(0, className.lastIndexOf("Impl")).replace(".impl", "");
    }

    default Class getAsyncInterfaceClass() throws ClassNotFoundException {
        String className = this.getClass().getName();
        return Class.forName(className.substring(0, className.lastIndexOf("Impl")).replace(".impl", ""));
    }
}
