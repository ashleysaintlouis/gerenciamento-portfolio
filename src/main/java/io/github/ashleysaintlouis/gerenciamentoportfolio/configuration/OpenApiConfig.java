package io.github.ashleysaintlouis.gerenciamentoportfolio.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gerenciamento de Portfólio")
                        .version("1.0")
                        .description("API para gerenciamento de projetos e membros de uma empresa")
                        .contact(new Contact()
                                .name("Ashley Saint-Louis")
                                .email("saintlouisashlhey998@gmail.com")
                                .url("https://github.com/ashleysaintlouis"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));

    }
}
