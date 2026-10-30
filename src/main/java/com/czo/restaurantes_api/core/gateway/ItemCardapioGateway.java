package com.czo.restaurantes_api.core.gateway;

import com.czo.restaurantes_api.core.domain.ItemCardapio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemCardapioGateway {

    Optional<ItemCardapio> obterPorId(UUID id);
    List<ItemCardapio> obterPorRestaurante(UUID restauranteId);
    ItemCardapio criar(ItemCardapio itemCardapio);
    ItemCardapio atualizar(ItemCardapio itemCardapio);
    void excluir(UUID id);
}
