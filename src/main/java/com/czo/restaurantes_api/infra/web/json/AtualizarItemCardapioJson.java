package com.czo.restaurantes_api.infra.web.json;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record AtualizarItemCardapioJson(
        @Schema(description = "Nome do item.", example = "Baião de dois", maxLength = 150)
        @NotBlank @Size(max = 150)
        String nome,

        @Schema(description = "Descrição do item.", example = "Prato da casa", maxLength = 1000)
        @NotBlank @Size(max = 1000)
        String descricao,

        @Schema(description = "Preço maior ou igual a 0,01.", example = "32.50", minimum = "0.01")
        @NotNull @DecimalMin(value = "0.01")
        BigDecimal preco,

        @Schema(description = "Indica se o item só pode ser consumido no restaurante.", example = "false")
        @NotNull
        Boolean apenasNoRestaurante,

        @Schema(description = "URL ou caminho da foto do item.", example = "https://exemplo.com/imagens/baiao.jpg", maxLength = 500)
        @NotBlank @Size(max = 500)
        String foto
) {
}
