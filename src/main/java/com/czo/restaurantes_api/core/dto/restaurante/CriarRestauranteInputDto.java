package com.czo.restaurantes_api.core.dto.restaurante;

import com.czo.restaurantes_api.core.dto.EnderecoInputDto;

import java.util.UUID;

public record CriarRestauranteInputDto(
        String nome,
        EnderecoInputDto endereco,
        String tipoCozinha,
        String horarioFuncionamento,
        UUID donoId) {
}
