package com.czo.restaurantes_api.infra.database.jpa;

import com.czo.restaurantes_api.core.domain.ItemCardapio;
import com.czo.restaurantes_api.core.gateway.ItemCardapioGateway;
import com.czo.restaurantes_api.infra.database.jpa.repository.ItemCardapioRepository;
import com.czo.restaurantes_api.infra.database.mapper.ItemCardapioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ItemCardapioJpaGateway implements ItemCardapioGateway {

    private final ItemCardapioRepository repository;
    private final ItemCardapioMapper mapper;

    @Override
    public Optional<ItemCardapio> obterPorId(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ItemCardapio> obterPorRestaurante(UUID restauranteId) {
        return repository.findByRestauranteId(restauranteId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public ItemCardapio criar(ItemCardapio itemCardapio) {
        return mapper.toDomain(repository.save(mapper.toEntity(itemCardapio)));
    }

    @Override
    public ItemCardapio atualizar(ItemCardapio itemCardapio) {
        if (!repository.existsById(itemCardapio.getId())) {
            throw new IllegalArgumentException("Item do cardápio não existe: " + itemCardapio.getId());
        }
        return mapper.toDomain(repository.save(mapper.toEntity(itemCardapio)));
    }

    @Override
    public void excluir(UUID id) {
        repository.deleteById(id);
    }
}
