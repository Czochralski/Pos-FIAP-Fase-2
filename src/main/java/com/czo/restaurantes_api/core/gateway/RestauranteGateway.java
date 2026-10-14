package com.czo.restaurantes_api.core.gateway;

import com.czo.restaurantes_api.core.domain.Restaurante;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestauranteGateway {

    Optional<Restaurante> obterPorId(UUID id);
    List<Restaurante> obterTodos();
    Restaurante criar(Restaurante restaurante);
    Restaurante atualizar(Restaurante restaurante);
    void excluir(UUID id);
}
