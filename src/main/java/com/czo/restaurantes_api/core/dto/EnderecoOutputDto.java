package com.czo.restaurantes_api.core.dto;

public record EnderecoOutputDto(
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String cep
){
}
