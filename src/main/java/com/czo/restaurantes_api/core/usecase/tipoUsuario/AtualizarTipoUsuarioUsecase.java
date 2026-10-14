package com.czo.restaurantes_api.core.usecase.tipoUsuario;

import com.czo.restaurantes_api.core.domain.TipoUsuario;
import com.czo.restaurantes_api.core.dto.tipoUsuario.AtualizarTipoUsuarioInputDto;
import com.czo.restaurantes_api.core.dto.tipoUsuario.TipoUsuarioOutputDto;
import com.czo.restaurantes_api.core.exception.TipoUsuarioJaExisteException;
import com.czo.restaurantes_api.core.exception.TipoUsuarioNaoEncontradoException;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;

import java.util.UUID;

public class AtualizarTipoUsuarioUsecase {

    private final TipoUsuarioGateway gateway;

    private AtualizarTipoUsuarioUsecase(TipoUsuarioGateway gateway) {

        this.gateway = gateway;
    }

    public static AtualizarTipoUsuarioUsecase create(TipoUsuarioGateway gateway) {

        return new AtualizarTipoUsuarioUsecase(gateway);
    }

    public TipoUsuarioOutputDto run(UUID id, AtualizarTipoUsuarioInputDto input) {

        TipoUsuario tipo = gateway.obterPorId(id)
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException(id));

        gateway.obterPorNome(input.nomeTipo())
                .filter(outro -> !outro.getId().equals(id))
                .ifPresent(outro -> {
                    throw new TipoUsuarioJaExisteException(input.nomeTipo());
                });

        tipo.atualizarNome(input.nomeTipo());
        TipoUsuario atualizado = gateway.atualizar(tipo);

        return new TipoUsuarioOutputDto(atualizado.getId(), atualizado.getNomeTipo());
    }
}
