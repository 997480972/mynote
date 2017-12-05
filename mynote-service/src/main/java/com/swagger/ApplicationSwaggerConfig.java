package com.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class ApplicationSwaggerConfig {

    @Bean
    public Docket addUserDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//        	 	.groupName("demo")
//                .genericModelSubstitutes(DeferredResult.class)
//              .genericModelSubstitutes(ResponseEntity.class)
//                .useDefaultResponseMessages(false)
//                .forCodeGeneration(false)
                .pathMapping("")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))
                .build();
        ApiInfo apiInfo = new ApiInfoBuilder()
                    .title("mynote-service API")//大标题
                    .description("REST API, all the applications could access the Object model data via JSON.")//详细描述
                    .version("1.0")//版本
                    .termsOfServiceUrl("")
                    .contact("206")//作者
//                    .license("The Apache License, Version 2.0")
//                    .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                    .build();
        docket.apiInfo(apiInfo);
        return docket;
    }
}
