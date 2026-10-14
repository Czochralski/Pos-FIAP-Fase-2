package com.czo.restaurantes_api.core.usecase.tipoUsuario;

import com.czo.restaurantes_api.core.dto.tipoUsuario.TipoUsuarioOutputDto;
import com.czo.restaurantes_api.core.exception.TipoUsuarioNaoEncontradoException;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;

import java.util.UUID;

public class BuscarTipoUsuarioUsecase {

    private final TipoUsuarioGateway gateway;

    private BuscarTipoUsuarioUsecase(TipoUsuarioGateway gateway) {

        this.gateway = gateway;
    }

    public static BuscarTipoUsuarioUsecase create(TipoUsuarioGateway gateway) {

        return new BuscarTipoUsuarioUsecase(gateway);
    }

    public TipoUsuarioOutputDto run(UUID id) {

        var tipo = gateway.obterPorId(id)
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException(id));

        return new TipoUsuarioOutputDto(tipo.getId(), tipo.getNomeTipo());
    }
}
