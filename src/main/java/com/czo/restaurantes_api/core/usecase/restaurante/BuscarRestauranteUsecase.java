package com.czo.restaurantes_api.core.usecase.restaurante;

import com.czo.restaurantes_api.core.domain.Restaurante;
import com.czo.restaurantes_api.core.dto.restaurante.RestauranteOutputDto;
import com.czo.restaurantes_api.core.exception.RestauranteNaoEncontradoException;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;

import java.util.UUID;

public class BuscarRestauranteUsecase {

    private final RestauranteGateway gateway;

    private BuscarRestauranteUsecase(RestauranteGateway gateway) {

        this.gateway = gateway;
    }

    public static BuscarRestauranteUsecase create(RestauranteGateway gateway) {

        return new BuscarRestauranteUsecase(gateway);
    }

    public RestauranteOutputDto run(UUID id) {

        Restaurante restaurante = gateway.obterPorId(id).orElseThrow(() ->
                new RestauranteNaoEncontradoException(id));

        return RestauranteOutputMapper.toOutput(restaurante);
    }
}
