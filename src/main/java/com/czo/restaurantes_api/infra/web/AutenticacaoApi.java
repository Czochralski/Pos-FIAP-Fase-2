package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.infra.security.UsuarioAutenticado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Autenticação")
public class AutenticacaoApi {

    @GetMapping("/me")
    @Operation(summary = "Consultar usuário autenticado",
            description = "Retorna o identificador, o login e o tipo associado às credenciais Basic da requisição.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário autenticado retornado.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas.")
    })
    public UsuarioLogadoOutputDto usuarioLogado(@AuthenticationPrincipal UsuarioAutenticado usuario) {

        return new UsuarioLogadoOutputDto(usuario.getId(), usuario.getUsername(), usuario.getTipoUsuario());
    }

    public record UsuarioLogadoOutputDto(
            @Schema(description = "UUID do usuário autenticado.", example = "44444444-4444-4444-4444-444444444444") UUID id,
            @Schema(description = "Login do usuário autenticado.", example = "dono") String login,
            @Schema(description = "Tipo de usuário associado à conta.", example = "DONO") String tipoUsuario) {
    }
}
