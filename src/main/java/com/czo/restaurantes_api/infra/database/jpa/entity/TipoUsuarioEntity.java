package com.czo.restaurantes_api.infra.database.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tipo_usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuarioEntity {

    @Id
    private UUID id;

    @Column(name = "nome_tipo", nullable = false, unique = true, length = 100)
    private String nomeTipo;

    @Column(name = "sistema", nullable = false)
    private boolean sistema;
}
