package com.czo.restaurantes_api.core.usecase.itemCardapio;

import com.czo.restaurantes_api.core.domain.ItemCardapio;
import com.czo.restaurantes_api.core.exception.ItemCardapioNaoEncontradoException;
import com.czo.restaurantes_api.core.exception.UsuarioSemPermissaoCardapioException;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;

import java.util.UUID;

public class ValidadorItemCardapio {

    private final UsuarioGateway usuarioGateway;
    private final RestauranteGateway restauranteGateway;

    private ValidadorItemCardapio(UsuarioGateway usuarioGateway, RestauranteGateway restauranteGateway) {

        this.usuarioGateway = usuarioGateway;
        this.restauranteGateway = restauranteGateway;
    }

    public static ValidadorItemCardapio create(UsuarioGateway usuarioGateway, RestauranteGateway restauranteGateway) {

        return new ValidadorItemCardapio(usuarioGateway,  restauranteGateway);
    }

    public void validarDonoDoRestaurante(UUID usuarioId, UUID restauranteId) {

        if (!usuarioGateway.usuarioDono(usuarioId)
                || !restauranteGateway.usuarioDono(
                usuarioId, restauranteId)) {
            throw new UsuarioSemPermissaoCardapioException();
        }
    }

    public static void validarRestaurante(ItemCardapio item, UUID restauranteId) {

        if (!item.getRestauranteId().equals(restauranteId)) {
            throw new ItemCardapioNaoEncontradoException(
                    item.getId());
        }
    }
}
