package com.db.auth.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * This class responsible for swagger configuration
 *
 * @author arVahedi
 */

@OpenAPIDefinition(
        info = @Info(title = "FPSBA AUTH-SERVER API", version = "v1"),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class OpenApiConfig implements BaseConfig {
    public static final String SWAGGER_HTML_URI = "swagger-ui/index.html";

    @Value("${springdoc.api-docs.enabled:true}")
    private boolean apiDocsEnabled;
    @Value("${springdoc.swagger-ui.path:null}")
    private String swaggerUiPath;
    @Value("${springdoc.api-docs.path:null}")
    private String apiDocsPath;

}
