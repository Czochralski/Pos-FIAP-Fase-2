package com.czo.restaurantes_api.core.usecase.tipoUsuario;

import com.czo.restaurantes_api.core.dto.tipoUsuario.ListaTiposUsuarioOutputDto;
import com.czo.restaurantes_api.core.dto.tipoUsuario.TipoUsuarioOutputDto;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;

public class ListarTiposUsuarioUsecase {

    private final TipoUsuarioGateway gateway;

    private ListarTiposUsuarioUsecase(TipoUsuarioGateway gateway) {

        this.gateway = gateway;
    }

    public static ListarTiposUsuarioUsecase create(TipoUsuarioGateway gateway) {

        return new ListarTiposUsuarioUsecase(gateway);
    }

    public ListaTiposUsuarioOutputDto run() {

        var tipos = gateway.obterTodos().stream()
                .map(tipo -> new TipoUsuarioOutputDto(tipo.getId(), tipo.getNomeTipo(), tipo.isSistema()))
                .toList();

        return new ListaTiposUsuarioOutputDto(tipos);
    }
}
