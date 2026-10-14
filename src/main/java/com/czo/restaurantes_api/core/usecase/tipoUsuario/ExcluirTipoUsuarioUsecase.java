package com.czo.restaurantes_api.core.usecase.tipoUsuario;

import com.czo.restaurantes_api.core.exception.TipoUsuarioNaoEncontradoException;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;

import java.util.UUID;

public class ExcluirTipoUsuarioUsecase {

    private final TipoUsuarioGateway gateway;

    private ExcluirTipoUsuarioUsecase(TipoUsuarioGateway gateway) {

        this.gateway = gateway;
    }

    public static ExcluirTipoUsuarioUsecase create(TipoUsuarioGateway gateway) {

        return new ExcluirTipoUsuarioUsecase(gateway);
    }

    public void run(UUID id) {

        gateway.obterPorId(id)
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException(id));

        gateway.excluir(id);
    }
}
