package com.czo.restaurantes_api.core.usecase;

import com.czo.restaurantes_api.core.dto.ListaTiposUsuarioOutputDto;
import com.czo.restaurantes_api.core.dto.TipoUsuarioOutputDto;
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
                .map(tipo -> new TipoUsuarioOutputDto(tipo.getId(), tipo.getNomeTipo()))
                .toList();
        return new ListaTiposUsuarioOutputDto(tipos);
    }
}
