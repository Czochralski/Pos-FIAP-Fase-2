package com.czo.restaurantes_api.infra.web.json;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record AtualizarItemCardapioJson(
        @NotBlank @Size(max = 150)
        String nome,

        @NotBlank @Size(max = 1000)
        String descricao,

        @NotNull @DecimalMin(value = "0.01")
        BigDecimal preco,

        @NotNull
        Boolean apenasNoRestaurante,

        @NotBlank @Size(max = 500)
        String foto
) {
}
