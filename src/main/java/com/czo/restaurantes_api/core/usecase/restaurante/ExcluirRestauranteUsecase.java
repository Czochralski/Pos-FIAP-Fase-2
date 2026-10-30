package com.czo.restaurantes_api.core.usecase.restaurante;

import com.czo.restaurantes_api.core.exception.DonoRestauranteInvalidoException;
import com.czo.restaurantes_api.core.exception.RestauranteNaoEncontradoException;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;

import java.util.UUID;

public class ExcluirRestauranteUsecase {

    private final RestauranteGateway restauranteGateway;
    private final UsuarioGateway usuarioGateway;

    private ExcluirRestauranteUsecase(RestauranteGateway restauranteGateway, UsuarioGateway usuarioGateway) {

        this.restauranteGateway = restauranteGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public static ExcluirRestauranteUsecase create(RestauranteGateway restauranteGateway, UsuarioGateway usuarioGateway) {

        return new ExcluirRestauranteUsecase(restauranteGateway, usuarioGateway);
    }

    public void run(UUID id, UUID usuarioId) {

        restauranteGateway.obterPorId(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));

        if (!usuarioGateway.usuarioDono(usuarioId) ||
                !restauranteGateway.usuarioDono(usuarioId, id)) {
            throw new DonoRestauranteInvalidoException();
        }

        restauranteGateway.excluir(id);
    }
}
