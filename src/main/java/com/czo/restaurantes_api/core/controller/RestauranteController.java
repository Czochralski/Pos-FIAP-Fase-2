package com.czo.restaurantes_api.core.controller;

import com.czo.restaurantes_api.core.dto.restaurante.*;
import com.czo.restaurantes_api.core.usecase.restaurante.*;

import java.util.UUID;

public class RestauranteController {
    private final CriarRestauranteUsecase criar;
    private final ListarRestaurantesUsecase listar;
    private final BuscarRestauranteUsecase buscar;
    private final AtualizarRestauranteUsecase atualizar;
    private final ExcluirRestauranteUsecase excluir;

    private RestauranteController(CriarRestauranteUsecase criar, ListarRestaurantesUsecase listar,
                                  BuscarRestauranteUsecase buscar, AtualizarRestauranteUsecase atualizar,
                                  ExcluirRestauranteUsecase excluir) {
        this.criar = criar;
        this.listar = listar;
        this.buscar = buscar;
        this.atualizar = atualizar;
        this.excluir = excluir;
    }

    public static RestauranteController create(CriarRestauranteUsecase criar, ListarRestaurantesUsecase listar,
                                               BuscarRestauranteUsecase buscar, AtualizarRestauranteUsecase atualizar,
                                               ExcluirRestauranteUsecase excluir) {

        return new RestauranteController(criar, listar, buscar, atualizar, excluir);
    }

    public RestauranteOutputInsertDto criar(CriarRestauranteInputDto input, UUID usuarioId) {

        return criar.run(input, usuarioId);
    }

    public ListaRestaurantesOutputDto listar() {

        return listar.run();
    }

    public RestauranteOutputDto buscar(UUID id) {

        return buscar.run(id);
    }

    public RestauranteOutputDto atualizar(UUID id, AtualizarRestauranteInputDto input, UUID usuarioId) {

        return atualizar.run(id, input, usuarioId);
    }

    public void excluir(UUID id, UUID usuarioId ) {

        excluir.run(id, usuarioId );
    }
}
