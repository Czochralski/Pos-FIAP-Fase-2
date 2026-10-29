package com.czo.restaurantes_api.infra.web.json;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public record EnderecoJson(

        @Schema(description = "Logradouro do endereço.", example = "Rua das Flores")
        @NotBlank
        String rua,

        @Schema(description = "Número do imóvel.", example = "123")
        @NotBlank
        String numero,

        @Schema(description = "Bairro.", example = "Centro")
        @NotBlank
        String bairro,

        @Schema(description = "Cidade.", example = "Recife")
        @NotBlank
        String cidade,

        @Schema(description = "Sigla ou nome do estado.", example = "PE")
        @NotBlank
        String estado,

        @Schema(description = "CEP.", example = "50000-000")
        @NotBlank
        String cep
) {
}
