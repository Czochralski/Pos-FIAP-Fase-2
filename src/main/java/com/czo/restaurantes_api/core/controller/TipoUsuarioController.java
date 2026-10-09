package com.czo.restaurantes_api.core.controller;

import com.czo.restaurantes_api.core.dto.AtualizarTipoUsuarioInputDto;
import com.czo.restaurantes_api.core.dto.CriarTipoUsuarioInputDto;
import com.czo.restaurantes_api.core.dto.ListaTiposUsuarioOutputDto;
import com.czo.restaurantes_api.core.dto.TipoUsuarioOutputDto;
import com.czo.restaurantes_api.core.usecase.*;

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

    public TipoUsuarioOutputDto criar(CriarTipoUsuarioInputDto input) {
        return criar.run(input);
    }

    public ListaTiposUsuarioOutputDto listar() {
        return listar.run();
    }

    public TipoUsuarioOutputDto buscar(UUID id) {
        return buscar.run(id);
    }

    public TipoUsuarioOutputDto atualizar(UUID id, AtualizarTipoUsuarioInputDto input) {
        return atualizar.run(id, input);
    }

    public void excluir(UUID id) {
        excluir.run(id);
    }
}
