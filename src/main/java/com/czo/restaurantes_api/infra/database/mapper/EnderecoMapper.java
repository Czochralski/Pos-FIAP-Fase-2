package com.czo.restaurantes_api.infra.database.mapper;

import com.czo.restaurantes_api.core.domain.Endereco;
import com.czo.restaurantes_api.infra.database.jpa.entity.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    public Endereco toDomain(EnderecoEntity entity) {

        return Endereco.reconstruir(
                entity.getId(), entity.getRua(), entity.getNumero(), entity.getBairro(),
                entity.getCidade(), entity.getEstado(), entity.getCep());
    }

    public EnderecoEntity toEntity(Endereco domain) {

        return new EnderecoEntity(
                domain.getId(), domain.getRua(), domain.getNumero(), domain.getBairro(),
                domain.getCidade(), domain.getEstado(), domain.getCep());
    }
}
