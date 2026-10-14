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
    public ListarRestaurantesUsecase listarRestaurantesUsecase(RestauranteGateway gateway) {

        return ListarRestaurantesUsecase.create(gateway);
    }

    @Bean
    public BuscarRestauranteUsecase buscarRestauranteUsecase(RestauranteGateway gateway) {

        return BuscarRestauranteUsecase.create(gateway);
    }

    @Bean
    public AtualizarRestauranteUsecase atualizarRestauranteUsecase(RestauranteGateway restauranteGateway,
                                                                   UsuarioGateway usuarioGateway) {

        return AtualizarRestauranteUsecase.create(restauranteGateway, usuarioGateway);
    }

    @Bean
    public ExcluirRestauranteUsecase excluirRestauranteUsecase(RestauranteGateway gateway) {

        return ExcluirRestauranteUsecase.create(gateway);
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
