package com.czo.restaurantes_api.core.controller;

import com.czo.restaurantes_api.core.dto.tipoUsuario.*;
import com.czo.restaurantes_api.core.usecase.tipoUsuario.*;

import java.util.UUID;

public class TipoUsuarioController {
    private final CriarTipoUsuarioUsecase criar;
    private final ListarTiposUsuarioUsecase listar;
    private final BuscarTipoUsuarioUsecase buscar;
    private final AtualizarTipoUsuarioUsecase atualizar;
    private final ExcluirTipoUsuarioUsecase excluir;

    private TipoUsuarioController(CriarTipoUsuarioUsecase criar,
                                  ListarTiposUsuarioUsecase listar,
                                  BuscarTipoUsuarioUsecase buscar,
                                  AtualizarTipoUsuarioUsecase atualizar,
                                  ExcluirTipoUsuarioUsecase excluir) {
        this.criar = criar;
        this.listar = listar;
        this.buscar = buscar;
        this.atualizar = atualizar;
        this.excluir = excluir;
    }

    public static TipoUsuarioController create(CriarTipoUsuarioUsecase criar,
                                               ListarTiposUsuarioUsecase listar,
                                               BuscarTipoUsuarioUsecase buscar,
                                               AtualizarTipoUsuarioUsecase atualizar,
                                               ExcluirTipoUsuarioUsecase excluir) {
        return new TipoUsuarioController(criar, listar, buscar, atualizar, excluir);
    }

    public TipoUsuarioOutputInsertDto criar(CriarTipoUsuarioInputDto input, UUID usuarioId) {
        return criar.run(input, usuarioId);
    }

    public ListaTiposUsuarioOutputDto listar() {
        return listar.run();
    }

    public TipoUsuarioOutputDto buscar(UUID id) {
        return buscar.run(id);
    }

    public TipoUsuarioOutputDto atualizar(UUID id, AtualizarTipoUsuarioInputDto input, UUID usuarioId) {
        return atualizar.run(id, input, usuarioId);
    }

    public void excluir(UUID id, UUID usuarioId) {
        excluir.run(id, usuarioId);
    }
}
