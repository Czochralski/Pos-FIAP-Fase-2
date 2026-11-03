package com.czo.restaurantes_api.infra.web.json;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record AtualizarRestauranteJson(

        @Schema(description = "Nome do restaurante.", example = "Restaurante Central")
        @NotBlank(message = "Campo Obrigatório")
        String nome,

        @Schema(description = "Endereço do restaurante.")
        @NotNull (message = "Campo Obrigatório")
        @Valid
        EnderecoJson endereco,

        @Schema(description = "Tipo de cozinha.", example = "Brasileira")
        @NotBlank(message = "Campo Obrigatório")
        String tipoCozinha,

        @Schema(description = "Horário de funcionamento.", example = "10:00 às 22:00")
        @NotBlank(message = "Campo Obrigatório")
        String horarioFuncionamento
) {
}
