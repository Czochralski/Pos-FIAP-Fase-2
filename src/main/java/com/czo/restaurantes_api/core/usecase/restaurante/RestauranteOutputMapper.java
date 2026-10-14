package com.czo.restaurantes_api.core.usecase.restaurante;

import com.czo.restaurantes_api.core.domain.Endereco;
import com.czo.restaurantes_api.core.domain.Restaurante;
import com.czo.restaurantes_api.core.dto.EnderecoOutputDto;
import com.czo.restaurantes_api.core.dto.restaurante.RestauranteOutputDto;

final class RestauranteOutputMapper {

    private RestauranteOutputMapper() {

    }

   public static RestauranteOutputDto toOutput(Restaurante r) {

        Endereco e = r.getEndereco();

        return new RestauranteOutputDto(r.getId(), r.getNome(),
                new EnderecoOutputDto(e.getId(), e.getRua(), e.getNumero(), e.getBairro(),
                        e.getCidade(), e.getEstado(), e.getCep()),
                r.getTipoCozinha(), r.getHorarioFuncionamento(), r.getDonoId());
    }
}
