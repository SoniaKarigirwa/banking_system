package rw.ac.rca.banking_system.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().addSecurityItem(
                        new SecurityRequirement().addList("bearer-key")
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearer-key",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .in(SecurityScheme.In.HEADER)
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title("Banking System API")
                        .description("Banking API Documentation")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sonia")
                                .url("https://www.instagram.com/_soniah_k/")
                                .email("karigirwasoonia0@gmail.com"))
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("banking")
                .pathsToMatch("/**")
                .packagesToScan("rw.ac.rca.banking_system.controllers")
                .build();
    }
}
