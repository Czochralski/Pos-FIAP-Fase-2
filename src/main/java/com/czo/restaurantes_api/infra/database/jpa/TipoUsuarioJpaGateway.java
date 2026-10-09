package com.czo.restaurantes_api.infra.database.jpa;

import com.czo.restaurantes_api.core.domain.TipoUsuario;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;
import com.czo.restaurantes_api.infra.database.jpa.repository.TipoUsuarioRepository;
import com.czo.restaurantes_api.infra.database.mapper.TipoUsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TipoUsuarioJpaGateway implements TipoUsuarioGateway {
    private final TipoUsuarioRepository repository;
    private final TipoUsuarioMapper mapper;

    @Override
    public Optional<TipoUsuario> obterPorId(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<TipoUsuario> obterPorNome(String nomeTipo) {
        return repository.findByNomeTipoIgnoreCase(nomeTipo).map(mapper::toDomain);
    }

    @Override
    public List<TipoUsuario> obterTodos() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public TipoUsuario criar(TipoUsuario tipoUsuario) {
        return mapper.toDomain(repository.save(mapper.toEntity(tipoUsuario)));
    }

    @Override
    public TipoUsuario atualizar(TipoUsuario tipoUsuario) {
        if (!repository.existsById(tipoUsuario.getId())) {
            throw new IllegalArgumentException("Tipo de usuário não existe");
        }
        return mapper.toDomain(repository.save(mapper.toEntity(tipoUsuario)));
    }

    @Override
    public void excluir(UUID id) {
        repository.deleteById(id);
    }
}
