package com.czo.restaurantes_api.infra.web.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarTipoUsuarioJson(

        @NotBlank(message = "Campo é obrigatório")
        @Size(max = 100, message = "Campo deve ter no máximo 100 caracteres")
        String nomeTipo
) {
}
