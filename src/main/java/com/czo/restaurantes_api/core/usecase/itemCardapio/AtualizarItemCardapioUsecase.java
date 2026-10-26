package com.czo.restaurantes_api.core.usecase.itemCardapio;

import com.czo.restaurantes_api.core.domain.ItemCardapio;
import com.czo.restaurantes_api.core.dto.itemCardapio.AtualizarItemCardapioInputDto;
import com.czo.restaurantes_api.core.dto.itemCardapio.ItemCardapioOutputDto;
import com.czo.restaurantes_api.core.exception.ItemCardapioNaoEncontradoException;
import com.czo.restaurantes_api.core.gateway.ItemCardapioGateway;

import java.util.UUID;

public class AtualizarItemCardapioUsecase {

    private final ItemCardapioGateway itemGateway;
    private final ValidadorItemCardapio validadorItemCardapio;

    private AtualizarItemCardapioUsecase(ItemCardapioGateway itemGateway,
                                         ValidadorItemCardapio validadorItemCardapio) {

        this.itemGateway = itemGateway;
        this.validadorItemCardapio = validadorItemCardapio;
    }

    public static AtualizarItemCardapioUsecase create(ItemCardapioGateway itemGateway,
                                                      ValidadorItemCardapio validadorItemCardapio) {

        return new AtualizarItemCardapioUsecase(itemGateway, validadorItemCardapio);
    }

    public ItemCardapioOutputDto run(UUID restauranteId, UUID itemCardapioId,
                                     AtualizarItemCardapioInputDto input, UUID usuarioId) {

        ItemCardapio item = itemGateway.obterPorId(itemCardapioId)
                .orElseThrow(() -> new ItemCardapioNaoEncontradoException(itemCardapioId));

        ValidadorItemCardapio.validarRestaurante(item, restauranteId);

        validadorItemCardapio.validarDonoDoRestaurante(usuarioId, restauranteId);

        item.atualizar(input.nome(), input.descricao(), input.preco(),
                input.apenasNoRestaurante(), input.foto());

        return ItemCardapioOutputMapper.toOutput(itemGateway.atualizar(item));
    }
}
