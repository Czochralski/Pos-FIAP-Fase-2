package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.infra.security.UsuarioAutenticado;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/auth")
public class AutenticacaoApi {

    @GetMapping("/me")
    public UsuarioLogadoOutputDto usuarioLogado(@AuthenticationPrincipal UsuarioAutenticado usuario) {

        return new UsuarioLogadoOutputDto(usuario.getId(), usuario.getUsername(), usuario.getTipoUsuario());
    }

    public record UsuarioLogadoOutputDto(UUID id, String login, String tipoUsuario) {
    }
}
