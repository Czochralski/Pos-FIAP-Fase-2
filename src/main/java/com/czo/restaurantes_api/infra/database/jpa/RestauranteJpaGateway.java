package com.czo.restaurantes_api.infra.database.jpa;

import com.czo.restaurantes_api.core.domain.Restaurante;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;
import com.czo.restaurantes_api.infra.database.jpa.repository.RestauranteRepository;
import com.czo.restaurantes_api.infra.database.mapper.RestauranteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestauranteJpaGateway implements RestauranteGateway {

    private final RestauranteRepository repository;
    private final RestauranteMapper mapper;

    @Override
    public Optional<Restaurante> obterPorId(UUID id) {

        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Restaurante> obterTodos() {

        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Restaurante criar(Restaurante restaurante) {

        return mapper.toDomain(repository.save(mapper.toEntity(restaurante)));
    }

    @Override
    public Restaurante atualizar(Restaurante restaurante) {

        if (!repository.existsById(restaurante.getId())) {
            throw new IllegalArgumentException("Restaurante não existe: " + restaurante.getId());
        }

        return mapper.toDomain(repository.save(mapper.toEntity(restaurante)));
    }

    @Override
    public void excluir(UUID id) {

        repository.deleteById(id);
    }

    @Override
    public boolean usuarioDono(UUID usuarioId, UUID restauranteId) {

        return repository.usuarioDono(usuarioId, restauranteId);
    }
}
