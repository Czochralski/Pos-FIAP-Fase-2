package com.czo.restaurantes_api.core.exception;

import java.util.UUID;

public class TipoUsuarioNaoEncontradoException extends RuntimeException {
    public TipoUsuarioNaoEncontradoException(UUID id) {
        super("Tipo de usuário não encontrado: " + id);
    }
}
