package com.czo.restaurantes_api.core.gateway;

import com.czo.restaurantes_api.core.domain.TipoUsuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TipoUsuarioGateway {

    Optional<TipoUsuario> obterPorId(UUID id);
    Optional<TipoUsuario> obterPorNome(String nomeTipo);
    List<TipoUsuario> obterTodos();
    TipoUsuario criar(TipoUsuario tipoUsuario);
    TipoUsuario atualizar(TipoUsuario tipoUsuario);
    void excluir(UUID id);
}
