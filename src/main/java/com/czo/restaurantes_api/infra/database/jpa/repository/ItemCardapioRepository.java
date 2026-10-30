package com.czo.restaurantes_api.infra.database.jpa.repository;

import com.czo.restaurantes_api.infra.database.jpa.entity.ItemCardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapioEntity, UUID> {

    List<ItemCardapioEntity> findByRestauranteId(UUID restauranteId);
}
