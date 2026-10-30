package com.czo.restaurantes_api.core.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Endereco {

    private final UUID id;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    private Endereco(UUID id, String rua, String numero, String bairro, String cidade,
                     String estado, String cep) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public static Endereco criar(String rua, String numero, String bairro, String cidade,
                                 String estado, String cep) {

        validar(rua, numero, bairro, cidade, estado, cep);

        return new Endereco(UUID.randomUUID(), rua.trim(), numero.trim(), bairro.trim(),
                cidade.trim(), estado.trim().toUpperCase(), cep.trim());
    }

    public static Endereco reconstruir(UUID id, String rua, String numero, String bairro,
                                       String cidade, String estado, String cep) {

        if (id == null)
            throw new IllegalArgumentException("Id do endereço é obrigatório");

        validar(rua, numero, bairro, cidade, estado, cep);

        return new Endereco(id, rua.trim(), numero.trim(), bairro.trim(), cidade.trim(),
                estado.trim().toUpperCase(), cep.trim());
    }

    public void atualizar(String rua, String numero, String bairro, String cidade, String estado,
                          String cep) {

        validar(rua, numero, bairro, cidade, estado, cep);

        this.rua = rua.trim();
        this.numero = numero.trim();
        this.bairro = bairro.trim();
        this.cidade = cidade.trim();
        this.estado = estado.trim().toUpperCase();
        this.cep = cep.trim();
    }

    private static void validar(String rua, String numero, String bairro, String cidade,
                                String estado, String cep) {

        validarTexto(rua, "Rua é obrigatória");
        validarTexto(numero, "Número é obrigatório");
        validarTexto(bairro, "Bairro é obrigatório");
        validarTexto(cidade, "Cidade é obrigatória");
        validarTexto(estado, "Estado é obrigatório");
        validarTexto(cep, "CEP é obrigatório");

        if (estado.trim().length() != 2) throw new IllegalArgumentException("Estado deve ter 2 caracteres");
    }

    private static void validarTexto(String valor, String mensagem) {

        if (valor == null || valor.isBlank()) throw new IllegalArgumentException(mensagem);
    }
}
