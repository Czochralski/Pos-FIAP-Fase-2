package com.czo.restaurantes_api.infra.web.json;

import jakarta.validation.constraints.NotBlank;

public record EnderecoJson(

        @NotBlank
        String rua,

        @NotBlank
        String numero,

        @NotBlank
        String bairro,

        @NotBlank
        String cidade,

        @NotBlank
        String estado,

        @NotBlank
        String cep
) {
}
