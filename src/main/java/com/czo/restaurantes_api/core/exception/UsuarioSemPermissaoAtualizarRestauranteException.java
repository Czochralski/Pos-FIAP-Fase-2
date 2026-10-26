package com.czo.restaurantes_api.core.exception;

public class UsuarioSemPermissaoAtualizarRestauranteException extends RuntimeException {

    public UsuarioSemPermissaoAtualizarRestauranteException() {
        super("Apenas o dono do restaurante pode atualizá-lo");
    }
}
