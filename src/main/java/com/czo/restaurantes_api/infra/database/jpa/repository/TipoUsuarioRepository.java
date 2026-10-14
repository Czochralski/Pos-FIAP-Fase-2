package com.czo.restaurantes_api.infra.database.jpa.repository;

import com.czo.restaurantes_api.infra.database.jpa.entity.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, UUID> {

    Optional<TipoUsuarioEntity> findByNomeTipoIgnoreCase(String nomeTipo);
}
