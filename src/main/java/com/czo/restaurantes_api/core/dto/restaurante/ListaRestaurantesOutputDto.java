package com.czo.restaurantes_api.core.dto.restaurante;

import java.util.List;

public record ListaRestaurantesOutputDto(
        List<RestauranteOutputDto> restaurantes) {
}
