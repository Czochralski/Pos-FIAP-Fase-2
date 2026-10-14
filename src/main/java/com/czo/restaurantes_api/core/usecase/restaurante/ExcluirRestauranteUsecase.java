package com.czo.restaurantes_api.core.usecase.restaurante;

import com.czo.restaurantes_api.core.exception.RestauranteNaoEncontradoException;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;

import java.util.UUID;

public class ExcluirRestauranteUsecase {

    private final RestauranteGateway gateway;

    private ExcluirRestauranteUsecase(RestauranteGateway gateway) {

        this.gateway = gateway;
    }

    public static ExcluirRestauranteUsecase create(RestauranteGateway gateway) {

        return new ExcluirRestauranteUsecase(gateway);
    }

    public void run(UUID id) {

        if (gateway.obterPorId(id).isEmpty()) {
            throw new RestauranteNaoEncontradoException(id);
        }
        gateway.excluir(id);
    }
}
