package com.yl.promethues.v.endpoint.server.model;

import io.vertx.core.json.Json;

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
 *@Description: 响应消息类
 *
 *@author  Orochi-Yzh
 *@dateTime  15/4/2020 15:15
*/
public class ResponsePacket<T> {

    private int code = 200;//状态

    private String msg = "SUCCESS";//消息

    private T data;

    public T getData() {
        return data;
    }

    public ResponsePacket setData(T data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResponsePacket setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponsePacket setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return Json.encode(this);
    }

    public static ResponsePacket build() {
        return new ResponsePacket();
    }
}
