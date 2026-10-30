package com.czo.restaurantes_api.core.dto.itemCardapio;

import java.math.BigDecimal;

public record AtualizarItemCardapioInputDto(
        String nome,
        String descricao,
        BigDecimal preco,
        boolean apenasNoRestaurante,
        String foto
){
}
