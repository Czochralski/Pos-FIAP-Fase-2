package com.czo.restaurantes_api.infra.database.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteEntity {

    public RestauranteEntity(UUID id, String nome, EnderecoEntity endereco, String tipoCozinha,
                             String horarioFuncionamento, UUID donoId) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.donoId = donoId;
    }

    @Id
    private UUID id;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "endereco_id", nullable = false, unique = true)
    private EnderecoEntity endereco;

    @Column(name = "tipo_cozinha", nullable = false, length = 100)
    private String tipoCozinha;

    @Column(name = "horario_funcionamento", nullable = false, length = 100)
    private String horarioFuncionamento;

    @Column(name = "dono_id", nullable = false)
    private UUID donoId;

    @OneToMany(
            mappedBy = "restaurante",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<ItemCardapioEntity> itensCardapio = new ArrayList<>();
}
