package com.czo.restaurantes_api.infra.database.jpa;

import com.czo.restaurantes_api.core.gateway.UsuarioGateway;
import com.czo.restaurantes_api.infra.database.jpa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioJpaGateway implements UsuarioGateway {
    private final UsuarioRepository repository;

    @Override
    public boolean usuarioDono(UUID usuarioId) {

        return usuarioId != null && repository.existeDonoPorId(usuarioId);
    }
}
