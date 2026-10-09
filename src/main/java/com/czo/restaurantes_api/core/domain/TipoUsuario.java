package com.czo.restaurantes_api.core.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TipoUsuario {

    private UUID id;
    private String nomeTipo;

    private TipoUsuario(UUID id, String nomeTipo) {
        this.id = id;
        this.nomeTipo = nomeTipo;
    }

    public static TipoUsuario criar(String nomeTipo) {
        validarNome(nomeTipo);
        return new TipoUsuario(UUID.randomUUID(), nomeTipo.trim());
    }

    public static TipoUsuario reconstruir(UUID id, String nomeTipo) {
        validarId(id);
        validarNome(nomeTipo);
        return new TipoUsuario(id, nomeTipo.trim());
    }

    public void atualizarNome(String nomeTipo) {
        validarNome(nomeTipo);
        this.nomeTipo = nomeTipo.trim();
    }

    private static void validarNome(String nomeTipo) {
        if (nomeTipo == null || nomeTipo.isBlank()) {
            throw new IllegalArgumentException("Nome do tipo de usuário é obrigatório");
        }
    }

    private static void validarId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id do tipo de usuário é obrigatório");
        }
    }
}
