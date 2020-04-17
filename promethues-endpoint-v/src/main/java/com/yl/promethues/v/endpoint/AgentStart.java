package com.yl.promethues.v.endpoint;

import com.yl.promethues.v.endpoint.listener.VertxEngine;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2019<br>
 * *
 *
 * @Description: 类描述
 * @date 2020/4/17 15:19
 * @Author: <a href= "765741668@qq.com">yangzonghua</a>
 */
public class AgentStart {

        public static void premain(String enable) {
            new VertxEngine().startEngine();
        }

}
