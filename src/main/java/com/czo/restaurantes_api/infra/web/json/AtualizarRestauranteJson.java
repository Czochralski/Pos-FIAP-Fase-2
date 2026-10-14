package com.czo.restaurantes_api.infra.web.json;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AtualizarRestauranteJson(

        @NotBlank
        String nome,

        @NotNull @Valid
        EnderecoJson endereco,

        @NotBlank
        String tipoCozinha,

        @NotBlank
        String horarioFuncionamento,

        @NotNull
        UUID donoId
) {
}
