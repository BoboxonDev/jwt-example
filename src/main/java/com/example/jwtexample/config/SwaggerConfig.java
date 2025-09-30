package com.example.jwtexample.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(securityComponents());
    }

    private Info apiInfo() {
        return new Info()
                .title("POS System API")
                .version("v1.0.0")
                .description("REST API documentation for POS system. \n\n" +
                        "Use the **Authorize** button and enter your JWT token " +
                        "in the format: `Bearer <your_token>`")
                .termsOfService("https://example.com/terms")
                .contact(new io.swagger.v3.oas.models.info.Contact()
                        .name("Support Team")
                        .email("support@example.com")
                        .url("https://example.com/support"))
                .license(new io.swagger.v3.oas.models.info.License()
                        .name("Apache 2.0")
                        .url("http://springdoc.org"));
    }

    private Components securityComponents() {
        return new Components()
                .addSecuritySchemes(SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("JWT tokenni kiriting. Format: **Bearer {token}**")
                );
    }
}
