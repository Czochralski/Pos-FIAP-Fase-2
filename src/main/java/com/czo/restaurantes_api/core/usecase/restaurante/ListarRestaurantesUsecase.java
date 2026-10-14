package com.czo.restaurantes_api.core.usecase.restaurante;

import com.czo.restaurantes_api.core.dto.restaurante.ListaRestaurantesOutputDto;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;

public class ListarRestaurantesUsecase {

    private final RestauranteGateway gateway;

    private ListarRestaurantesUsecase(RestauranteGateway gateway) {

        this.gateway = gateway;
    }

    public static ListarRestaurantesUsecase create(RestauranteGateway gateway) {

        return new ListarRestaurantesUsecase(gateway);
    }

    public ListaRestaurantesOutputDto run() {

        var restaurantes = gateway.obterTodos()
                .stream()
                .map(RestauranteOutputMapper::toOutput)
                .toList();

        return new ListaRestaurantesOutputDto(restaurantes);
    }
}
