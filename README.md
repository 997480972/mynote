# mynote
前端：Bootstrap + Vue.js + JQuery.js + FreeMarker模板引擎
后端：Spring Boot + Spring Cloud + Spring Data JPA + Mysql
 
mynote-common 公共实体，工具类
mynote-eureka 服务治理项目,服务注册和发现模块 采用Spring Cloud Netflix Eureka 配置了9090端口
mynote-service 服务提供者,对外服务API项目, 数据访问层,Spring Data JPA,Eureka server 从每个client实例接收心跳消息。如果心跳超时，则通常将该实例从注册server中删除。配了9091端口
mynote-web web模块(服务消费者),采用Feign(默认实现了负载均衡的效果,自带断路器) + 断路器（Hystrix）。配置了9092端口

#启动方式（3种）
mynote下mvn install把所有模块打包并发布到本地仓库
1.mvn clean spring-boot:run #spring boot maven插件启动方式，需在聚合工程执行mvn install把模块打包并发布到本地仓库后，才能引进模块依赖，否则会找不到依赖
2.java -jar XXX-exec.jar（正式环境推荐使用）
3.直接运行相应模块启动类的main方法（开发时推荐使用）

#启动顺序
1.先启动注册中心mynote-eureka
2.再启动mynote-service 服务提供者，mynote-web web模块(服务消费者)