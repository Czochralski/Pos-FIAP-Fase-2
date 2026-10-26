package com.czo.restaurantes_api.core.usecase.itemCardapio;

import com.czo.restaurantes_api.core.dto.itemCardapio.ListaItensCardapioOutputDto;
import com.czo.restaurantes_api.core.gateway.ItemCardapioGateway;

import java.util.UUID;

public class ListarItensCardapioUsecase {

    private final ItemCardapioGateway gateway;

    private ListarItensCardapioUsecase(ItemCardapioGateway gateway) {
        this.gateway = gateway;
    }

    public static ListarItensCardapioUsecase create(ItemCardapioGateway gateway) {
        return new ListarItensCardapioUsecase(gateway);
    }

    public ListaItensCardapioOutputDto run(UUID restauranteId) {

        return new ListaItensCardapioOutputDto(gateway.obterPorRestaurante(restauranteId).stream()
                .map(ItemCardapioOutputMapper::toOutput).toList());
    }
}
