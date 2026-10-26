package com.czo.restaurantes_api.core.exception;

public class TipoUsuarioSistemaProtegidoException extends RuntimeException {

    public TipoUsuarioSistemaProtegidoException() {
        super("Tipos de usuário do sistema não podem ser alterados ou removidos");
    }
}
