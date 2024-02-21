package com.springboot.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v1.0.0")
                .title("API 타이틀")
                .description("API Description");

        // SecurityScheme 명
        String jwtSchemeName = "jwtAuth";
        // API 요청 헤더에 인증 정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        Server server1 = new Server().url("https://patient-care-diary.dev");
        Server server2 = new Server().url("http://patient-care-diary.dev");
        Server server3 = new Server().url("http://patient-care-diary.dev:8080");
        Server server4 = new Server().url("http://34.47.100.44:8080");
        List<Server> servers = new ArrayList<>();
        servers.add(server1);
        servers.add(server2);
        servers.add(server3);
        servers.add(server4);
        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components)
                .servers(servers);
    }
}
