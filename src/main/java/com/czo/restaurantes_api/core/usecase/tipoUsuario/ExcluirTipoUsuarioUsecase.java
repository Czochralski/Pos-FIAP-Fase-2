package com.czo.restaurantes_api.core.usecase.tipoUsuario;

import com.czo.restaurantes_api.core.exception.DonoRestauranteInvalidoException;
import com.czo.restaurantes_api.core.exception.TipoUsuarioNaoEncontradoException;
import com.czo.restaurantes_api.core.exception.TipoUsuarioSistemaProtegidoException;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;

import java.util.UUID;

public class ExcluirTipoUsuarioUsecase {

    private final TipoUsuarioGateway gateway;
    private final UsuarioGateway usuarioGateway;

    private ExcluirTipoUsuarioUsecase(TipoUsuarioGateway gateway,UsuarioGateway usuarioGateway) {

        this.gateway = gateway;
        this.usuarioGateway = usuarioGateway;
    }

    public static ExcluirTipoUsuarioUsecase create(TipoUsuarioGateway gateway, UsuarioGateway usuarioGateway) {

        return new ExcluirTipoUsuarioUsecase(gateway, usuarioGateway);
    }

    public void run(UUID id, UUID usuarioId) {

        if (!usuarioGateway.usuarioDono(usuarioId)) {
            throw new DonoRestauranteInvalidoException();
        }

        var tipo = gateway.obterPorId(id)
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException(id));

        if (tipo.isSistema()) {
            throw new TipoUsuarioSistemaProtegidoException();
        }

        gateway.excluir(tipo.getId());
    }
}
