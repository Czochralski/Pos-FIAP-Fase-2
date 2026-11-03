package com.czo.restaurantes_api.infra.web.json;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public record EnderecoJson(

        @Schema(description = "Logradouro do endereço.", example = "Rua das Flores")
        @NotBlank(message = "Campo Obrigatório")
        String rua,

        @Schema(description = "Número do imóvel.", example = "123")
        @NotBlank(message = "Campo Obrigatório")
        String numero,

        @Schema(description = "Bairro.", example = "Centro")
        @NotBlank(message = "Campo Obrigatório")
        String bairro,

        @Schema(description = "Cidade.", example = "Recife")
        @NotBlank(message = "Campo Obrigatório")
        String cidade,

        @Schema(description = "Sigla ou nome do estado.", example = "PE")
        @NotBlank(message = "Campo Obrigatório")
        String estado,

        @Schema(description = "CEP.", example = "50000-000")
        @NotBlank(message = "Campo Obrigatório")
        String cep
) {
}
