package com.czo.restaurantes_api.core.dto.itemCardapio;

import java.util.List;

public record ListaItensCardapioOutputDto(
        List<ItemCardapioOutputDto> itens) {
}
