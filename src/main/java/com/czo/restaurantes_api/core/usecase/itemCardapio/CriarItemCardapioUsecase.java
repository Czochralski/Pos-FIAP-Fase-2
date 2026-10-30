package com.czo.restaurantes_api.core.usecase.itemCardapio;

import com.czo.restaurantes_api.core.domain.ItemCardapio;
import com.czo.restaurantes_api.core.dto.itemCardapio.CriarItemCardapioInputDto;
import com.czo.restaurantes_api.core.dto.itemCardapio.ItemCardapioOutputInsertDto;
import com.czo.restaurantes_api.core.gateway.ItemCardapioGateway;

import java.util.UUID;

public class CriarItemCardapioUsecase {

    private final ItemCardapioGateway itemGateway;
    private final ValidadorItemCardapio validadorItemCardapio;

    private CriarItemCardapioUsecase(ItemCardapioGateway itemGateway,
                                     ValidadorItemCardapio validadorItemCardapio) {
        this.itemGateway = itemGateway;
        this.validadorItemCardapio = validadorItemCardapio;
    }

    public static CriarItemCardapioUsecase create(ItemCardapioGateway itemGateway,
                                                  ValidadorItemCardapio validadorItemCardapio) {
        return new CriarItemCardapioUsecase(itemGateway, validadorItemCardapio);
    }

    public ItemCardapioOutputInsertDto run(UUID restauranteId, CriarItemCardapioInputDto input, UUID usuarioId) {

        validadorItemCardapio.validarDonoDoRestaurante(usuarioId, restauranteId);
        ItemCardapio item = ItemCardapio.criar(input.nome(), input.descricao(), input.preco(),
                input.apenasNoRestaurante(), input.foto(), restauranteId);

        return ItemCardapioOutputMapper.toOutputInsert(itemGateway.criar(item));
    }

}
