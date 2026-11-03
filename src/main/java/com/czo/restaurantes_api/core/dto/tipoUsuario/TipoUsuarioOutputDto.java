package com.czo.restaurantes_api.core.dto.tipoUsuario;

import java.util.UUID;

public record TipoUsuarioOutputDto(
        String nomeTipo,
        boolean sistema
) {
}
