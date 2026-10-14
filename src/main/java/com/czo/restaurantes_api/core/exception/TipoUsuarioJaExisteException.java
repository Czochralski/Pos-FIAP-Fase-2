package com.czo.restaurantes_api.core.exception;

public class TipoUsuarioJaExisteException extends RuntimeException {

    public TipoUsuarioJaExisteException(String nomeTipo) {
        super("Tipo de usuário já existe: " + nomeTipo);
    }
}
