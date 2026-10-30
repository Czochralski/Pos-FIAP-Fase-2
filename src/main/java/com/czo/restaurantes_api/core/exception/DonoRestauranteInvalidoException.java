package com.czo.restaurantes_api.core.exception;

public class DonoRestauranteInvalidoException extends RuntimeException {

    public DonoRestauranteInvalidoException() {
        super("O usuário logado não é o dono do restaurante");
    }
}
