package com.orochi.vertx.controller;

import com.orochi.vertx.core.anno.RouteHandler;
import com.orochi.vertx.core.anno.RouteMapping;
import com.orochi.vertx.core.anno.RouteMethod;
import com.orochi.vertx.core.model.ResponsePacket;
import com.orochi.vertx.core.utils.AsyncServiceUtil;
import com.orochi.vertx.core.utils.HttpUtil;
import com.orochi.vertx.core.utils.ParamUtil;
import com.orochi.vertx.core.vertx.VertxSingleton;
import com.orochi.vertx.entity.User;
import com.orochi.vertx.service2.UserTwoAsyncService;
import com.orochi.vertx.service.UserAsyncService;
import io.vertx.core.Handler;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.Set;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;

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
 *@dateTime  1/4/2020 4:25 PM
*/
@RouteHandler("restapp")
public class UserController {

    private UserAsyncService userAsyncService = AsyncServiceUtil.getAsyncServiceInstance(UserAsyncService.class);

    private UserTwoAsyncService userTwoAsyncService = AsyncServiceUtil.getAsyncServiceInstance(UserTwoAsyncService.class);

//    /**
//     * 演示过滤器
//     *
//     * @return
//     */
//    @RouteMapping(value = "/*", method = RouteMethod.ROUTE, order = 2)
//    public Handler<RoutingContext> appFilter() {
//        return ctx -> {
//            System.err.println("appFilter过滤器！");
//            ctx.next();
//        };
//    }

    /**
     * 演示路径参数
     *
     * @return
     */
    @RouteMapping(value = "/test/:id", method = RouteMethod.GET)
    public Handler<RoutingContext> myTest() {
        return ctx -> {
//            JsonObject param = ParamUtil.getRequestParams(ctx);
            HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK, ResponsePacket.build().setMsg("Hello，欢迎使用测试地址.....").setData("test"));
        };
    }

    /**
     * 演示服务调用
     *
     * @return
     */
    @RouteMapping(value = "/findByAge", method = RouteMethod.GET)
    public Handler<RoutingContext> findByAge() {
        return ctx -> {
            JsonObject param = ParamUtil.getRequestParams(ctx);
            userAsyncService.findByAge(param.getInteger("age"), ar -> {
                if (ar.succeeded()) {
                    List<User> UserList = ar.result();
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK, ResponsePacket.build().setData(UserList));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_INTERNAL_ERROR,
                            ResponsePacket.build().setData(ar.cause().getMessage()).setCode(HTTP_INTERNAL_ERROR));
                }
            });
        };
    }

    @RouteMapping(value = "/findUserById/:id", method = RouteMethod.GET)
    public Handler<RoutingContext> findUserById() {
        return ctx -> {
            JsonObject param = ParamUtil.getRequestParams(ctx);
            if (!param.containsKey("id")) {
                HttpUtil.fireJsonResponse(ctx.response(), HTTP_INTERNAL_ERROR,
                        ResponsePacket.build().setMsg("缺少id参数").setCode(HTTP_INTERNAL_ERROR));
                return;
            }
            userTwoAsyncService.findUser(Long.valueOf(param.getString("id")), ar -> {
                if (ar.succeeded()) {
                    User User = ar.result();
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK, ResponsePacket.build().setData(User));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_INTERNAL_ERROR,
                            ResponsePacket.build().setData(ar.cause().getMessage()).setCode(HTTP_INTERNAL_ERROR));
                }
            });
        };
    }

    /**
     * 演示文件上传
     *
     * @return
     */
    @RouteMapping(value = "/upload", method = RouteMethod.POST)
    public Handler<RoutingContext> upload() {
        return ctx -> {
            Set<FileUpload> uploads = ctx.fileUploads();
            FileSystem fs = VertxSingleton.getInstance().fileSystem();
            uploads.forEach(fileUpload -> {
                String path = "D:/vertxupload/" + fileUpload.fileName();
                fs.copy(fileUpload.uploadedFileName(), path, ar -> {
                    if (ar.succeeded()) {
                        fs.deleteBlocking(fileUpload.uploadedFileName());
                    }
                });
            });
            HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK, ResponsePacket.build().setData("OK"));
        };
    }

}
