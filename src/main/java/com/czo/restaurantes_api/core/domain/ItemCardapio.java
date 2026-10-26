package com.czo.restaurantes_api.core.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class ItemCardapio {

    private final UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean apenasNoRestaurante;
    private String foto;
    private final UUID restauranteId;


    private ItemCardapio(UUID id, String nome, String descricao, BigDecimal preco,
                         boolean apenasNoRestaurante, String foto, UUID restauranteId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.apenasNoRestaurante = apenasNoRestaurante;
        this.foto = foto;
        this.restauranteId = restauranteId;
    }

    public static ItemCardapio criar(String nome, String descricao, BigDecimal preco, boolean apenasNoRestaurante,
                                     String foto, UUID restauranteId){

        validar(nome, descricao, preco, foto, restauranteId);
        return new ItemCardapio(UUID.randomUUID(), nome.trim(), descricao.trim(), preco,
                apenasNoRestaurante, foto.trim(), restauranteId);
    }

    public static ItemCardapio reconstruir(UUID id, String nome, String descricao, BigDecimal preco,
                                           boolean apenasNoRestaurante, String foto, UUID restauranteId) {

        if (id == null)
            throw new IllegalArgumentException("Id do Item é obrigatório");

        validar(nome, descricao, preco, foto, restauranteId);

        return new ItemCardapio(id, nome.trim(), descricao.trim(), preco, apenasNoRestaurante, foto.trim(), restauranteId);
    }

    public void atualizar(String nome, String descricao, BigDecimal preco, boolean apenasNoRestaurante,
                          String foto) {

        validar(nome, descricao, preco, foto, restauranteId);

        this.nome = nome.trim();
        this.descricao = descricao.trim();
        this.preco = preco;
        this.apenasNoRestaurante = apenasNoRestaurante;
        this.foto = foto.trim();
    }

    private static void validar(String nome, String descricao, BigDecimal preco, String foto, UUID restauranteId) {

        validarTexto(nome, "Nome é obrigatório");
        validarTexto(descricao, "Descrição é obrigatória");
        validarPreco(preco);
        validarTexto(foto, "Caminho da foto é obrigatório");
        validarTexto(String.valueOf(restauranteId), "Restaurante do item é obrigatório");
    }

    private static void validarPreco(BigDecimal preco) {
        if (preco == null) {
            throw new IllegalArgumentException("Preço é obrigatório");
        }

        if (preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
    }

    private static void validarTexto(String valor, String mensagem) {

        if (valor == null || valor.isBlank())
            throw new IllegalArgumentException(mensagem);
    }
}
