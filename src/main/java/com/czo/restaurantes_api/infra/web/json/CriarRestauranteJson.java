package com.czo.restaurantes_api.infra.web.json;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarRestauranteJson(

        @NotBlank
        String nome,

        @NotNull @Valid
        EnderecoJson endereco,

        @NotBlank
        String tipoCozinha,

        @NotBlank
        String horarioFuncionamento
) {
}
