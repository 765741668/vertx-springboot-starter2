对于了传统同步阻塞型架构spring-boot1.5，性能提升有着巨幅提升，同样配置的单体服务提升到4.6w吞吐量，添加压测客户端仍有明显的上升空间
相比于spring-boot2.x的reactor编程风格有异曲同工之处，但QPS并发能力不在一个等级
vertx以前在生产环境验证过，轻松支撑起了百万级别的设备心跳对接工作，借此迎合spring-boot开发出starter

已实现功能

api路由功能
请求方式对标GET POST DELETE等兼容
rest api兼容与参数解析
与各中间件交互能力(mongo,mysql,redis等)
对标MVC controller编程风格设计
...

待续

统一异常处理
相关HttpServer参数性能优化
项目属于雏形阶段，更优雅的设计调整
项目中揉进了测试用的业务逻辑，需要调整
README完善
