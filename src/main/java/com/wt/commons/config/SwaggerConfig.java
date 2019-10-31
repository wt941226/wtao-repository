package com.wt.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 接口文档配置类
 *
 * @author wtao
 * @date 2019-10-20 13:21
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createAdminApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("后台管理接口")
                .apiInfo(apiAdminInfo())
                .select()
                //.apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.wt.blog.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiAdminInfo() {
        Contact contact = new Contact("王涛", "www.wt.com", "1083877365@qq.com");
        return new ApiInfoBuilder()
                .title("博客项目后台接口文档")
                .description("博客项目后台接口文档")
                // 联系方式
                .contact(contact)
                // 版本
                .version("1.0.0")
                .build();
    }

    /*@Bean
    public Docket createAppApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("App端接口")
                .apiInfo(apiAppInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wt.blog.controller.app"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiAppInfo() {
        Contact contact = new Contact("王涛", "www.wt.com", "1083877365@qq.com");
        return new ApiInfoBuilder()
                .title("博客项目APP端接口文档")
                .description("博客项目APP端接口文档")
                // 联系方式
                .contact(contact)
                // 版本
                .version("1.0.0")
                .build();
    }*/
}