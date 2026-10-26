package com.czo.restaurantes_api.core.usecase.itemCardapio;

import com.czo.restaurantes_api.core.domain.ItemCardapio;
import com.czo.restaurantes_api.core.exception.ItemCardapioNaoEncontradoException;
import com.czo.restaurantes_api.core.gateway.ItemCardapioGateway;

import java.util.UUID;

public class ExcluirItemCardapioUsecase {

    private final ItemCardapioGateway itemGateway;
    private final ValidadorItemCardapio validadorItemCardapio;

    private ExcluirItemCardapioUsecase(ItemCardapioGateway itemGateway,
                                       ValidadorItemCardapio validadorItemCardapio) {

        this.itemGateway = itemGateway;
        this.validadorItemCardapio = validadorItemCardapio;

    }

    public static ExcluirItemCardapioUsecase create(ItemCardapioGateway itemGateway,
                                                    ValidadorItemCardapio validadorItemCardapio) {
        return new ExcluirItemCardapioUsecase(itemGateway, validadorItemCardapio);
    }

    public void run(UUID restauranteId, UUID itemCardapioId, UUID usuarioId) {

        ItemCardapio item = itemGateway.obterPorId(itemCardapioId)
                .orElseThrow(() -> new ItemCardapioNaoEncontradoException(itemCardapioId));

        ValidadorItemCardapio.validarRestaurante(item, restauranteId);

        validadorItemCardapio.validarDonoDoRestaurante(usuarioId, restauranteId);

        itemGateway.excluir(itemCardapioId);
    }
}
