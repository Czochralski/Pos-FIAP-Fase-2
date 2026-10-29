package com.czo.restaurantes_api.infra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Restaurantes — Tech Challenge Fase 2",
                version = "1.0.0",
                description = "API REST para autenticação, gerenciamento de restaurantes, tipos de usuário "
                        + "e itens de cardápio. As rotas da API exigem autenticação HTTP Basic. "
                        + "As regras de autorização são aplicadas conforme o perfil e a propriedade do restaurante."
        ),
        security = @SecurityRequirement(name = "basicAuth"),
        tags = {
                @Tag(name = "Autenticação", description = "Identidade do usuário autenticado."),
                @Tag(name = "Restaurantes", description = "Cadastro e gerenciamento de restaurantes."),
                @Tag(name = "Tipos de usuário", description = "Consulta e gerenciamento de tipos de usuário."),
                @Tag(name = "Itens de cardápio", description = "Itens associados a um restaurante.")
        }
)
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic",
        description = "Informe o login e a senha do usuário da aplicação."
)
public class OpenApiConfiguration {
}
