package com.czo.restaurantes_api.core.exception;

import java.util.UUID;

public class RestauranteNaoEncontradoException extends RuntimeException {

    public RestauranteNaoEncontradoException(UUID id) {
        super("Restaurante não encontrado: " + id);
    }
}
