package com.panduoma.pdmadmin.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author 潘多码(微信 : panduoma888)
 * @version 1.0.0
 * @description   接口文档配置类
 * @website www.panduoma.com
 * @copyright 公众号: 潘多码
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SpringBoot3")
                        .version("1.0")
                        .description( "SpringBoot3+Vue3脚手架接口文档")
                        .contact(new Contact().name("gjx").url("www.gjx.com")));
    }
}