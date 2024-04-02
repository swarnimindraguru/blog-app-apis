package com.swarnim.blog.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Collections;

@Configuration
public class SwaggerConfig {
//    @Bean
//    public Docket api(){
//
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
//    }
//
//    private ApiInfo getInfo() {
//        return new ApiInfo("Blogging Application","This project is developed by Swarnim", "1.0", "Terms of Service", new Contact("Swarnim","swarnim.com","swarnim@gmail.com"),"Licence of APIs","API license URL", Collections.emptyList());
//    };
private SecurityScheme createAPIKeyScheme(){
    return new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("Bearer");
}

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication",createAPIKeyScheme()))
                .info(new Info().title("Blogging Application ")
                        .description("This Project Is Developed By Swarnim Indraguru")
                        .version("1.0")
                        .termsOfService("Terms of Service")
                        .contact(new Contact().name("Swarnim Indraguru").email("swarnimindraguru@gmail.com"))
                        .license( new License().name("License of API"))  )
                .servers(Collections.singletonList(new Server().url("http://localhost:9090/")));
    }
}

