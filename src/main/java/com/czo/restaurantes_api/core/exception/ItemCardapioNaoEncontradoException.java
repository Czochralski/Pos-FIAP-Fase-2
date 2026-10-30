package com.czo.restaurantes_api.core.exception;

import java.util.UUID;

public class ItemCardapioNaoEncontradoException extends RuntimeException {

    public ItemCardapioNaoEncontradoException(UUID id) {
        super("Item do cardápio não encontrado: " + id);
    }
}
