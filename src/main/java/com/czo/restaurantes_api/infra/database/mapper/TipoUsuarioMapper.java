package com.czo.restaurantes_api.infra.database.mapper;

import com.czo.restaurantes_api.core.domain.TipoUsuario;
import com.czo.restaurantes_api.infra.database.jpa.entity.TipoUsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class TipoUsuarioMapper {
    public TipoUsuario toDomain(TipoUsuarioEntity entity) {
        return TipoUsuario.reconstruir(
                entity.getId(),
                entity.getNomeTipo());
    }

    public TipoUsuarioEntity toEntity(TipoUsuario domain) {
        return new TipoUsuarioEntity(
                domain.getId(),
                domain.getNomeTipo());
    }
}
