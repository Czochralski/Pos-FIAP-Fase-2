package com.czo.restaurantes_api.core.exception;

import java.util.UUID;

public class DonoRestauranteInvalidoException extends RuntimeException {

    public DonoRestauranteInvalidoException(UUID id) {
        super("O usuário informado não existe ou não possui o tipo DONO: " + id);
    }
}
