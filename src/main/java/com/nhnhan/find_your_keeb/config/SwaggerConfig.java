package com.nhnhan.find_your_keeb.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Find Your Keeb API")
                        .description("""
                                A comprehensive e-commerce backend API for selling mechanical keyboards.
                                
                                ## Features
                                - **Authentication & Authorization**: JWT-based authentication with role-based access control
                                - **Product Management**: Advanced filtering by layout, price, brand, and features
                                - **Shopping Cart**: Full cart management with persistence
                                - **Order Management**: Complete checkout process and order tracking
                                - **Admin Panel**: Product and order management for administrators
                                
                                ## Authentication
                                Most endpoints require authentication. Include the JWT token in the Authorization header:
                                ```
                                Authorization: Bearer <your-jwt-token>
                                ```
                                
                                ## Getting Started
                                1. Register a new user: `POST /api/auth/register`
                                2. Login to get JWT token: `POST /api/auth/login`
                                3. Use the token in subsequent requests
                                
                                ## Default Admin Account
                                - Username: `admin`
                                - Password: `admin123`
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Find Your Keeb Team")
                                .email("support@findyourkeeb.com")
                                .url("https://findyourkeeb.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Development Server"),
                        new Server().url("https://api.findyourkeeb.com").description("Production Server")
                ))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Enter your JWT token")));
    }
} 