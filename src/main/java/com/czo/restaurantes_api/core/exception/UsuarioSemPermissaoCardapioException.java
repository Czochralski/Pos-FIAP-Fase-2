package com.czo.restaurantes_api.core.exception;

public class UsuarioSemPermissaoCardapioException extends RuntimeException {

    public UsuarioSemPermissaoCardapioException() {
        super("Apenas o dono do restaurante pode realizar alterações no cardápio");
    }
}
