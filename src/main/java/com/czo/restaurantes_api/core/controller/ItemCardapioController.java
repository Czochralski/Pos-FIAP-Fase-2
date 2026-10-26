package com.czo.restaurantes_api.core.controller;

import com.czo.restaurantes_api.core.dto.itemCardapio.*;
import com.czo.restaurantes_api.core.usecase.itemCardapio.*;

import java.util.UUID;

public class ItemCardapioController {

    private final CriarItemCardapioUsecase criar;
    private final ListarItensCardapioUsecase listar;
    private final BuscarItemCardapioUsecase buscar;
    private final AtualizarItemCardapioUsecase atualizar;
    private final ExcluirItemCardapioUsecase excluir;

    private ItemCardapioController(CriarItemCardapioUsecase criar, ListarItensCardapioUsecase listar,
                                   BuscarItemCardapioUsecase buscar, AtualizarItemCardapioUsecase atualizar,
                                   ExcluirItemCardapioUsecase excluir) {
        this.criar = criar;
        this.listar = listar;
        this.buscar = buscar;
        this.atualizar = atualizar;
        this.excluir = excluir;
    }

    public static ItemCardapioController create(CriarItemCardapioUsecase criar, ListarItensCardapioUsecase listar,
                                                BuscarItemCardapioUsecase buscar,
                                                AtualizarItemCardapioUsecase atualizar,
                                                ExcluirItemCardapioUsecase excluir) {
        return new ItemCardapioController(criar, listar, buscar, atualizar, excluir);
    }

    public ItemCardapioOutputInsertDto criar(UUID restauranteId, CriarItemCardapioInputDto input, UUID usuarioId) {
        return criar.run(restauranteId, input, usuarioId);
    }

    public ListaItensCardapioOutputDto listar(UUID restauranteId) {
        return listar.run(restauranteId);
    }

    public ItemCardapioOutputDto buscar(UUID restauranteId,UUID itemCardapioId) {
        return buscar.run(restauranteId, itemCardapioId);
    }

    public ItemCardapioOutputDto atualizar(UUID restauranteId, UUID itemCardapioId,
                                           AtualizarItemCardapioInputDto input, UUID usuarioId) {
        return atualizar.run(restauranteId, itemCardapioId, input, usuarioId);
    }

    public void excluir(UUID restauranteId, UUID itemCardapioId, UUID usuarioId) {
        excluir.run(restauranteId, itemCardapioId, usuarioId);
    }
}
