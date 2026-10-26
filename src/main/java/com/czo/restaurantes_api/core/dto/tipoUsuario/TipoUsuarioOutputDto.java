package com.czo.restaurantes_api.core.dto.tipoUsuario;

import java.util.UUID;

public record TipoUsuarioOutputDto(
        UUID id,
        String nomeTipo,
        boolean sistema
) {
}
