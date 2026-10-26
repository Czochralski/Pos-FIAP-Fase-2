package com.czo.restaurantes_api.infra.database.jpa.repository;

import com.czo.restaurantes_api.infra.database.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, UUID> {

    @Query(value = "SELECT EXISTS (SELECT 1 FROM restaurantes r " +
            "JOIN usuarios u ON u.id = r.dono_id " +
            "JOIN tipo_usuarios t ON t.id = u.tipo_usuario_id " +
            "WHERE r.id = :restauranteId AND u.id = :usuarioId " +
            "AND UPPER(t.nome_tipo) = 'DONO')", nativeQuery = true)
    boolean usuarioDono(@Param("usuarioId") UUID usuarioId, @Param("restauranteId") UUID restauranteId);
}
