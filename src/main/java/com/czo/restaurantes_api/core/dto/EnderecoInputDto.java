package com.czo.restaurantes_api.core.dto;

public record EnderecoInputDto(
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep
){
}
