package com.czo.restaurantes_api.infra.web.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

public record CriarTipoUsuarioJson(

        @Schema(description = "Nome único para o tipo de usuário. CLIENTE e DONO já são tipos de sistema.", example = "GERENTE", maxLength = 100)
        @NotBlank(message = "Campo é obrigatório")
        @Size(max = 100, message = "Campo deve ter no máximo 100 caracteres")
        String nomeTipo
) {
}
