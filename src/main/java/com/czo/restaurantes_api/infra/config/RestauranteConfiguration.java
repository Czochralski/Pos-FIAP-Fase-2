package com.czo.restaurantes_api.infra.config;

import com.czo.restaurantes_api.core.controller.RestauranteController;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;
import com.czo.restaurantes_api.core.usecase.restaurante.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestauranteConfiguration {

    @Bean
    public CriarRestauranteUsecase criarRestauranteUsecase(RestauranteGateway restauranteGateway,
                                                           UsuarioGateway usuarioGateway) {

        return CriarRestauranteUsecase.create(restauranteGateway, usuarioGateway);
    }

    @Bean
    public ListarRestaurantesUsecase listarRestaurantesUsecase(RestauranteGateway restauranteGateway) {

        return ListarRestaurantesUsecase.create(restauranteGateway);
    }

    @Bean
    public BuscarRestauranteUsecase buscarRestauranteUsecase(RestauranteGateway restauranteGateway) {

        return BuscarRestauranteUsecase.create(restauranteGateway);
    }

    @Bean
    public AtualizarRestauranteUsecase atualizarRestauranteUsecase(RestauranteGateway restauranteGateway) {

        return AtualizarRestauranteUsecase.create(restauranteGateway);
    }

    @Bean
    public ExcluirRestauranteUsecase excluirRestauranteUsecase(RestauranteGateway restauranteGateway,
                                                               UsuarioGateway usuarioGateway) {

        return ExcluirRestauranteUsecase.create(restauranteGateway, usuarioGateway);
    }

    @Bean
    public RestauranteController restauranteController(CriarRestauranteUsecase criar,
                                                       ListarRestaurantesUsecase listar,
                                                       BuscarRestauranteUsecase buscar,
                                                       AtualizarRestauranteUsecase atualizar,
                                                       ExcluirRestauranteUsecase excluir) {

        return RestauranteController.create(criar, listar, buscar, atualizar, excluir);
    }
}
