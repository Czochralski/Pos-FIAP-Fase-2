package com.czo.restaurantes_api.core.dto.restaurante;
import com.czo.restaurantes_api.core.dto.EnderecoOutputDto;

import java.util.UUID;
public record RestauranteOutputDto(
        UUID id,
        String nome,
        EnderecoOutputDto endereco,
        String tipoCozinha,
        String horarioFuncionamento,
        UUID donoId) {
}
