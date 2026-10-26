package com.czo.restaurantes_api.core.exception;

public class UsuarioSemPermissaoCriarRestauranteException extends RuntimeException {

    public UsuarioSemPermissaoCriarRestauranteException() {
        super("Apenas usuários com perfil DONO podem cadastrar restaurantes");
    }
}
