package com.czo.restaurantes_api.core.usecase.itemCardapio;

import com.czo.restaurantes_api.core.dto.itemCardapio.ItemCardapioOutputDto;
import com.czo.restaurantes_api.core.exception.ItemCardapioNaoEncontradoException;
import com.czo.restaurantes_api.core.gateway.ItemCardapioGateway;

import java.util.UUID;

public class BuscarItemCardapioUsecase {

    private final ItemCardapioGateway gateway;

    private BuscarItemCardapioUsecase(ItemCardapioGateway gateway) {
        this.gateway = gateway;
    }

    public static BuscarItemCardapioUsecase create(ItemCardapioGateway gateway) {
        return new BuscarItemCardapioUsecase(gateway);
    }

    public ItemCardapioOutputDto run(UUID restauranteId, UUID itemCardapioId) {

        var item = gateway.obterPorId(itemCardapioId).orElseThrow(() ->
                new ItemCardapioNaoEncontradoException(itemCardapioId));

        ValidadorItemCardapio.validarRestaurante(item, restauranteId);

        return ItemCardapioOutputMapper.toOutput(item);
    }
}
