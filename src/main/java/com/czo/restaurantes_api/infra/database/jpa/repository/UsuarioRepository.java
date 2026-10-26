package com.czo.restaurantes_api.infra.database.jpa.repository;

import com.czo.restaurantes_api.infra.database.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {

    @Query("SELECT u FROM UsuarioEntity u JOIN FETCH u.tipoUsuario WHERE LOWER(u.login) = LOWER(:login)")
    Optional<UsuarioEntity> buscarPorLogin(@Param("login") String login);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM usuarios u " +
            "JOIN tipo_usuarios t ON t.id = u.tipo_usuario_id " +
            "WHERE u.id = :usuarioId AND UPPER(t.nome_tipo) = 'DONO')", nativeQuery = true)
    boolean existeDonoPorId(@Param("usuarioId") UUID usuarioId);
}
