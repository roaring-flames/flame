package com.flame.common.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
 * @author king
 * @date 2022年01月20日 10:10
 * @DOTO:
 */
@RefreshScope
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Knife4jConfig {

    @Value(value = "${swagger.url:null}")
    private String swaggerUrl;

    @Value(value = "${swagger.description:null}")
    private String descriptiont;

    @Value(value = "${swagger.version:v1.1.0}")
    private String version;

    @Value(value = "${swagger.author:flame}")
    private String author;

    @Value(value = "${swagger.title:null}")
    private String title;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerUrl))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description(descriptiont)
                .contact(new Contact(author, "https://github.com.lenve", "1916008067@qq.com"))
                .version(version)
                .title(title)
                .build();
    }
}
