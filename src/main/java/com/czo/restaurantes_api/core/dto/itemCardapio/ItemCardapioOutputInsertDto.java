package com.czo.restaurantes_api.core.dto.itemCardapio;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCardapioOutputInsertDto(
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        boolean apenasNoRestaurante,
        String foto
) {
}

