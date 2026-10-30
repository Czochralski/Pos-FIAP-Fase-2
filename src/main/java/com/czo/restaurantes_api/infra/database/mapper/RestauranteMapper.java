package com.czo.restaurantes_api.infra.database.mapper;

import com.czo.restaurantes_api.core.domain.Restaurante;
import com.czo.restaurantes_api.infra.database.jpa.entity.RestauranteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestauranteMapper {
    private final EnderecoMapper enderecoMapper;

    public Restaurante toDomain(RestauranteEntity entity) {

        return Restaurante.reconstruir(
                entity.getId(), entity.getNome(), enderecoMapper.toDomain(entity.getEndereco()),
                entity.getTipoCozinha(), entity.getHorarioFuncionamento(), entity.getDonoId());
    }

    public RestauranteEntity toEntity(Restaurante domain) {

        return new RestauranteEntity(
                domain.getId(), domain.getNome(), enderecoMapper.toEntity(domain.getEndereco()),
                domain.getTipoCozinha(), domain.getHorarioFuncionamento(), domain.getDonoId());
    }
}
