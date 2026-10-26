package com.czo.restaurantes_api.core.dto.tipoUsuario;

import java.util.UUID;

public record TipoUsuarioOutputInsertDto(
        UUID id,
        String nomeTipo,
        boolean sistema
) {
}
