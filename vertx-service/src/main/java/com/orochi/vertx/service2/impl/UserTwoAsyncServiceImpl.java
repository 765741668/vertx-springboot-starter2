package com.orochi.vertx.service2.impl;

import com.orochi.vertx.core.anno.AsyncServiceHandler;
import com.orochi.vertx.core.model.BaseAsyncService;
import com.orochi.vertx.entity.User;
import com.orochi.vertx.service.UserService;
import com.orochi.vertx.service2.UserTwoAsyncService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
 *@dateTime  1/4/2020 4:29 PM
*/
@AsyncServiceHandler
@Component
public class UserTwoAsyncServiceImpl implements UserTwoAsyncService, BaseAsyncService {

    @Autowired
    private UserService userService;

    @Override
    public void findUser(Long id, Handler<AsyncResult<User>> resultHandler) {
        try {
            User User = userService.findById(id);
            Future.succeededFuture(User).setHandler(resultHandler);
        } catch (Exception e) {
            e.printStackTrace();
            resultHandler.handle(Future.failedFuture(e));
        }
    }
}
