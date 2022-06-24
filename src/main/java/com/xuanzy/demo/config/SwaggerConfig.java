package com.xuanzy.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Author: 轩志颍
 * Create: 2022-06-23 14:01
 **/

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(adminApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xuanzy.demo.controller")) // 设置扫描路径
                .build();
    }
    private ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("后台管理系统--API文档")
                .description("后台管理系统接口描述")
                .version("1.0")
                .contact(new Contact("轩志颍","http://localhost:80","xy818632@163.com"))
                .build();
    }
}
