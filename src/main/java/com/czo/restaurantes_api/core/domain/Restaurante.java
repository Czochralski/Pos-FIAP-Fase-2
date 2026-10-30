package com.czo.restaurantes_api.core.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Restaurante {

    private final UUID id;
    private String nome;
    private Endereco endereco;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private UUID donoId;

    private Restaurante(UUID id, String nome, Endereco endereco, String tipoCozinha,
                        String horarioFuncionamento, UUID donoId) {

        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.donoId = donoId;
    }

    public static Restaurante criar(String nome, Endereco endereco, String tipoCozinha,
                                    String horarioFuncionamento, UUID donoId) {

        validarCampos(nome, endereco, tipoCozinha, horarioFuncionamento, donoId);

        return new Restaurante(UUID.randomUUID(), nome.trim(), endereco, tipoCozinha.trim(),
                horarioFuncionamento.trim(), donoId);
    }

    public static Restaurante reconstruir(UUID id, String nome, Endereco endereco, String tipoCozinha,
                                          String horarioFuncionamento, UUID donoId) {

        if (id == null)
            throw new IllegalArgumentException("Id do restaurante é obrigatório");

        validarCampos(nome, endereco, tipoCozinha, horarioFuncionamento, donoId);

        return new Restaurante(id, nome.trim(), endereco, tipoCozinha.trim(),
        horarioFuncionamento.trim(), donoId);
    }

    public void atualizar(String nome, Endereco endereco, String tipoCozinha,
                          String horarioFuncionamento, UUID donoId) {

        validarCampos(nome, endereco, tipoCozinha, horarioFuncionamento, donoId);

        this.nome = nome.trim();
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha.trim();
        this.horarioFuncionamento = horarioFuncionamento.trim();
        this.donoId = donoId;
    }

    private static void validarCampos(String nome, Endereco endereco, String tipoCozinha,
                                      String horarioFuncionamento, UUID donoId) {

        validarTexto(nome, "Nome do restaurante é obrigatório");

        if (endereco == null)
            throw new IllegalArgumentException("Endereço do restaurante é obrigatório");

        validarTexto(tipoCozinha, "Tipo de cozinha é obrigatório");
        validarTexto(horarioFuncionamento, "Horário de funcionamento é obrigatório");

        if (donoId == null)
            throw new IllegalArgumentException("Dono do restaurante é obrigatório");
    }

    private static void validarTexto(String valor, String mensagem) {

        if (valor == null || valor.isBlank())
            throw new IllegalArgumentException(mensagem);
    }
}
