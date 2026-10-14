package com.czo.restaurantes_api.infra.database.jpa.repository;

import com.czo.restaurantes_api.infra.database.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, UUID> {
}
