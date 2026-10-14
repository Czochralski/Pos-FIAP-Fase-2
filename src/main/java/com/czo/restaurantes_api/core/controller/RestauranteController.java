package com.czo.restaurantes_api.core.controller;

import com.czo.restaurantes_api.core.dto.restaurante.AtualizarRestauranteInputDto;
import com.czo.restaurantes_api.core.dto.restaurante.CriarRestauranteInputDto;
import com.czo.restaurantes_api.core.dto.restaurante.ListaRestaurantesOutputDto;
import com.czo.restaurantes_api.core.dto.restaurante.RestauranteOutputDto;
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

    public RestauranteOutputDto criar(CriarRestauranteInputDto input) {
        return criar.run(input);
    }

    public ListaRestaurantesOutputDto listar() {
        return listar.run();
    }

    public RestauranteOutputDto buscar(UUID id) {
        return buscar.run(id);
    }

    public RestauranteOutputDto atualizar(UUID id, AtualizarRestauranteInputDto input) {
        return atualizar.run(id, input);
    }

    public void excluir(UUID id) {
        excluir.run(id);
    }
}
