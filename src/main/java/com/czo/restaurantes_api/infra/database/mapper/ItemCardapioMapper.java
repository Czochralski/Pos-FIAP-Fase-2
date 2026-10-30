package com.czo.restaurantes_api.infra.database.mapper;

import com.czo.restaurantes_api.core.domain.ItemCardapio;
import com.czo.restaurantes_api.infra.database.jpa.entity.ItemCardapioEntity;
import com.czo.restaurantes_api.infra.database.jpa.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemCardapioMapper {

    private final RestauranteRepository restauranteRepository;

    public ItemCardapio toDomain(ItemCardapioEntity entity) {

        return ItemCardapio.reconstruir(entity.getId(), entity.getNome(), entity.getDescricao(), entity.getPreco(),
                entity.isApenasNoRestaurante(), entity.getFoto(), entity.getRestaurante().getId());
    }

    public ItemCardapioEntity toEntity(ItemCardapio domain) {

        var restaurante = restauranteRepository.getReferenceById(domain.getRestauranteId());
        return new ItemCardapioEntity(domain.getId(), domain.getNome(), domain.getDescricao(), domain.getPreco(),
                domain.isApenasNoRestaurante(), domain.getFoto(), restaurante);
    }
}
