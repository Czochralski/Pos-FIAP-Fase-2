package com.czo.restaurantes_api.infra.config;

import com.czo.restaurantes_api.core.controller.ItemCardapioController;
import com.czo.restaurantes_api.core.gateway.ItemCardapioGateway;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;
import com.czo.restaurantes_api.core.usecase.itemCardapio.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemCardapioConfiguration {

    @Bean
    public CriarItemCardapioUsecase criarItemCardapioUsecase(ItemCardapioGateway itemGateway,
                                                             ValidadorItemCardapio validadorItemCardapio) {
        return CriarItemCardapioUsecase.create(itemGateway, validadorItemCardapio);
    }

    @Bean
    public ListarItensCardapioUsecase listarItensCardapioUsecase(ItemCardapioGateway gateway) {
        return ListarItensCardapioUsecase.create(gateway);
    }

    @Bean
    public BuscarItemCardapioUsecase buscarItemCardapioUsecase(ItemCardapioGateway gateway) {
        return BuscarItemCardapioUsecase.create(gateway);
    }

    @Bean
    public AtualizarItemCardapioUsecase atualizarItemCardapioUsecase(ItemCardapioGateway itemGateway,
                                                                     ValidadorItemCardapio validadorItemCardapio) {
        return AtualizarItemCardapioUsecase.create(itemGateway, validadorItemCardapio);
    }

    @Bean
    public ExcluirItemCardapioUsecase excluirItemCardapioUsecase(ItemCardapioGateway itemGateway,
                                                                 ValidadorItemCardapio validadorItemCardapio) {
        return ExcluirItemCardapioUsecase.create(itemGateway, validadorItemCardapio);
    }

    @Bean
    public ItemCardapioController itemCardapioController(CriarItemCardapioUsecase criar,
                                                         ListarItensCardapioUsecase listar,
                                                         BuscarItemCardapioUsecase buscar,
                                                         AtualizarItemCardapioUsecase atualizar,
                                                         ExcluirItemCardapioUsecase excluir) {
        return ItemCardapioController.create(criar, listar, buscar, atualizar, excluir);
    }

    @Bean
    public ValidadorItemCardapio validadorItemCardapio(UsuarioGateway usuarioGateway,
                                                       RestauranteGateway restauranteGateway) {

        return ValidadorItemCardapio.create(usuarioGateway, restauranteGateway);
    }
}
