package com.example.contactlistback.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

// https://medium.com/@javedalikhan50/comprehensive-guide-to-openapi-swagger-integration-in-spring-boot-with-spring-security-jwt-edf8c84e7d91
// https://dev.to/erwanlt/migration-from-swagger-2-to-openapi-3-1060

/**
 * Swagger configuration through annotations
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Contact List Api",
                version = "1.0",
                description = "This is the contact list api documentation.",
                contact = @Contact (
                        name = "Sara Mahía",
                        url = "https://smahia.github.io/"
                )
        )
)
public class SwaggerConfig {
}
