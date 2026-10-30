package com.czo.restaurantes_api.core.usecase.tipoUsuario;

import com.czo.restaurantes_api.core.domain.TipoUsuario;
import com.czo.restaurantes_api.core.dto.tipoUsuario.AtualizarTipoUsuarioInputDto;
import com.czo.restaurantes_api.core.dto.tipoUsuario.TipoUsuarioOutputDto;
import com.czo.restaurantes_api.core.exception.DonoRestauranteInvalidoException;
import com.czo.restaurantes_api.core.exception.TipoUsuarioJaExisteException;
import com.czo.restaurantes_api.core.exception.TipoUsuarioNaoEncontradoException;
import com.czo.restaurantes_api.core.exception.TipoUsuarioSistemaProtegidoException;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;

import java.util.UUID;

public class AtualizarTipoUsuarioUsecase {

    private final TipoUsuarioGateway gateway;
    private final UsuarioGateway usuarioGateway;

    private AtualizarTipoUsuarioUsecase(TipoUsuarioGateway gateway, UsuarioGateway usuarioGateway) {

        this.gateway = gateway;
        this.usuarioGateway = usuarioGateway;
    }

    public static AtualizarTipoUsuarioUsecase create(TipoUsuarioGateway gateway, UsuarioGateway usuarioGateway) {

        return new AtualizarTipoUsuarioUsecase(gateway, usuarioGateway);
    }

    public TipoUsuarioOutputDto run(UUID id, AtualizarTipoUsuarioInputDto input, UUID usuarioId) {

        if (!usuarioGateway.usuarioDono(usuarioId)) {
            throw new DonoRestauranteInvalidoException();
        }

        TipoUsuario tipo = gateway.obterPorId(id)
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException(id));

        if (tipo.isSistema()) {
            throw new TipoUsuarioSistemaProtegidoException();
        }

        String nomeTipo = input.nomeTipo().trim();
        gateway.obterPorNome(nomeTipo)
                .filter(tipoExistente -> !tipoExistente.getId().equals(id))
                .ifPresent(tipoExistente -> {
                    throw new TipoUsuarioJaExisteException(nomeTipo);
                });

        tipo.atualizarNome(nomeTipo);

        TipoUsuario atualizado = gateway.atualizar(tipo);

        return new TipoUsuarioOutputDto(atualizado.getId(), atualizado.getNomeTipo(), atualizado.isSistema());
    }
}
