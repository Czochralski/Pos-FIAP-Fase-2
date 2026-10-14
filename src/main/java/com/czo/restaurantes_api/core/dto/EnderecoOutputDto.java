package com.czo.restaurantes_api.core.dto;

import java.util.UUID;

public record EnderecoOutputDto(
        UUID id,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep){
}
