package com.czo.restaurantes_api.core.usecase.itemCardapio;

import com.czo.restaurantes_api.core.domain.ItemCardapio;
import com.czo.restaurantes_api.core.dto.itemCardapio.ItemCardapioOutputDto;
import com.czo.restaurantes_api.core.dto.itemCardapio.ItemCardapioOutputInsertDto;

public class ItemCardapioOutputMapper {

    private ItemCardapioOutputMapper() {
    }

    public static ItemCardapioOutputDto toOutput(ItemCardapio item) {

        return new ItemCardapioOutputDto(
                item.getNome(), item.getDescricao(), item.getPreco(),
                item.isApenasNoRestaurante(), item.getFoto());
    }

    public static ItemCardapioOutputInsertDto toOutputInsert(ItemCardapio item) {

        return new ItemCardapioOutputInsertDto(
                item.getId(), item.getNome(), item.getDescricao(), item.getPreco(),
                item.isApenasNoRestaurante(), item.getFoto());
    }
}
