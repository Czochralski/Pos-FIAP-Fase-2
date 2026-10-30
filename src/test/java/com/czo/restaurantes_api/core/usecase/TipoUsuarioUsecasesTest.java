package com.czo.restaurantes_api.core.usecase;

import com.czo.restaurantes_api.TestFixtures;
import com.czo.restaurantes_api.core.domain.TipoUsuario;
import com.czo.restaurantes_api.core.dto.tipoUsuario.AtualizarTipoUsuarioInputDto;
import com.czo.restaurantes_api.core.dto.tipoUsuario.CriarTipoUsuarioInputDto;
import com.czo.restaurantes_api.core.exception.*;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;
import com.czo.restaurantes_api.core.usecase.tipoUsuario.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioUsecasesTest {

    @Mock TipoUsuarioGateway gateway;
    @Mock UsuarioGateway usuarioGateway;
    private UUID donoId;

    @BeforeEach
    void setUp() {
        donoId = TestFixtures.OWNER_ID;
    }

    @Test
    void criaTipoQuandoDonoEInexistente() {
        when(usuarioGateway.usuarioDono(donoId)).thenReturn(true);
        when(gateway.obterPorNome("GERENTE")).thenReturn(Optional.empty());
        when(gateway.criar(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var criado = CriarTipoUsuarioUsecase.create(gateway, usuarioGateway)
                .run(new CriarTipoUsuarioInputDto(" GERENTE "), donoId);

        assertNotNull(criado.id());
        assertEquals("GERENTE", criado.nomeTipo());
        assertFalse(criado.sistema());
        verify(gateway).criar(any(TipoUsuario.class));
    }

    @Test
    void criarTipoRejeitaUsuarioSemPermissaoENomeDuplicado() {
        when(usuarioGateway.usuarioDono(donoId)).thenReturn(false);
        assertThrows(DonoRestauranteInvalidoException.class, () -> CriarTipoUsuarioUsecase.create(gateway, usuarioGateway)
                .run(new CriarTipoUsuarioInputDto("GERENTE"), donoId));
        verifyNoInteractions(gateway);

        when(usuarioGateway.usuarioDono(donoId)).thenReturn(true);
        when(gateway.obterPorNome("DONO")).thenReturn(Optional.of(TestFixtures.tipo("DONO", true)));
        assertThrows(TipoUsuarioJaExisteException.class, () -> CriarTipoUsuarioUsecase.create(gateway, usuarioGateway)
                .run(new CriarTipoUsuarioInputDto("DONO"), donoId));
        verify(gateway, never()).criar(any());
    }

    @Test
    void listaTiposEBuscaPorId() {
        TipoUsuario gerente = TestFixtures.tipo("GERENTE", false);
        when(gateway.obterTodos()).thenReturn(List.of(gerente));
        when(gateway.obterPorId(gerente.getId())).thenReturn(Optional.of(gerente));

        var lista = ListarTiposUsuarioUsecase.create(gateway).run();
        var encontrado = BuscarTipoUsuarioUsecase.create(gateway).run(gerente.getId());

        assertEquals(1, lista.tipos().size());
        assertEquals("GERENTE", lista.tipos().getFirst().nomeTipo());
        assertFalse(lista.tipos().getFirst().sistema());
        assertEquals(gerente.getId(), encontrado.id());
        assertThrows(TipoUsuarioNaoEncontradoException.class,
                () -> BuscarTipoUsuarioUsecase.create(gateway).run(UUID.randomUUID()));
    }

    @Test
    void atualizaTipoValidandoPermissaoExistenciaProtecaoEDuplicidade() {
        UUID tipoId = UUID.randomUUID();
        TipoUsuario gerente = TipoUsuario.reconstruir(tipoId, "GERENTE", false);
        when(usuarioGateway.usuarioDono(donoId)).thenReturn(true);
        when(gateway.obterPorId(tipoId)).thenReturn(Optional.of(gerente));
        when(gateway.obterPorNome("GESTOR")).thenReturn(Optional.empty());
        when(gateway.atualizar(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var atualizado = AtualizarTipoUsuarioUsecase.create(gateway, usuarioGateway)
                .run(tipoId, new AtualizarTipoUsuarioInputDto(" GESTOR "), donoId);
        assertEquals("GESTOR", atualizado.nomeTipo());
        assertEquals(tipoId, atualizado.id());

        when(usuarioGateway.usuarioDono(donoId)).thenReturn(false);
        assertThrows(DonoRestauranteInvalidoException.class,
                () -> AtualizarTipoUsuarioUsecase.create(gateway, usuarioGateway)
                        .run(tipoId, new AtualizarTipoUsuarioInputDto("OUTRO"), donoId));

        when(usuarioGateway.usuarioDono(donoId)).thenReturn(true);
        UUID ausente = UUID.randomUUID();
        when(gateway.obterPorId(ausente)).thenReturn(Optional.empty());
        assertThrows(TipoUsuarioNaoEncontradoException.class,
                () -> AtualizarTipoUsuarioUsecase.create(gateway, usuarioGateway)
                        .run(ausente, new AtualizarTipoUsuarioInputDto("OUTRO"), donoId));

        UUID sistemaId = UUID.randomUUID();
        when(gateway.obterPorId(sistemaId)).thenReturn(Optional.of(TipoUsuario.reconstruir(sistemaId, "DONO", true)));
        assertThrows(TipoUsuarioSistemaProtegidoException.class,
                () -> AtualizarTipoUsuarioUsecase.create(gateway, usuarioGateway)
                        .run(sistemaId, new AtualizarTipoUsuarioInputDto("OUTRO"), donoId));

        UUID outroId = UUID.randomUUID();
        when(gateway.obterPorId(outroId)).thenReturn(Optional.of(TipoUsuario.reconstruir(outroId, "OUTRO", false)));
        when(gateway.obterPorNome("GERENTE")).thenReturn(Optional.of(gerente));
        assertThrows(TipoUsuarioJaExisteException.class,
                () -> AtualizarTipoUsuarioUsecase.create(gateway, usuarioGateway)
                        .run(outroId, new AtualizarTipoUsuarioInputDto("GERENTE"), donoId));
    }

    @Test
    void atualizarTipoSemAlterarNomeNaoSeConfundeComDuplicidadeDeOutroRegistro() {
        UUID tipoId = UUID.randomUUID();
        TipoUsuario tipo = TipoUsuario.reconstruir(tipoId, "GERENTE", false);
        when(usuarioGateway.usuarioDono(donoId)).thenReturn(true);
        when(gateway.obterPorId(tipoId)).thenReturn(Optional.of(tipo));
        when(gateway.obterPorNome("GERENTE")).thenReturn(Optional.of(tipo));
        when(gateway.atualizar(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var resultado = AtualizarTipoUsuarioUsecase.create(gateway, usuarioGateway)
                .run(tipoId, new AtualizarTipoUsuarioInputDto("GERENTE"), donoId);

        assertEquals(tipoId, resultado.id());
        assertEquals("GERENTE", resultado.nomeTipo());
        verify(gateway).atualizar(tipo);
    }

    @Test
    void excluirTipoValidandoPermissaoExistenciaEProtecao() {
        ExcluirTipoUsuarioUsecase excluir = ExcluirTipoUsuarioUsecase.create(gateway, usuarioGateway);
        UUID id = UUID.randomUUID();
        when(usuarioGateway.usuarioDono(donoId)).thenReturn(true);
        when(gateway.obterPorId(id)).thenReturn(Optional.of(TipoUsuario.reconstruir(id, "GERENTE", false)));

        assertDoesNotThrow(() -> excluir.run(id, donoId));
        verify(gateway).excluir(id);

        when(usuarioGateway.usuarioDono(donoId)).thenReturn(false);
        assertThrows(DonoRestauranteInvalidoException.class, () -> excluir.run(id, donoId));

        when(usuarioGateway.usuarioDono(donoId)).thenReturn(true);
        UUID inexistente = UUID.randomUUID();
        when(gateway.obterPorId(inexistente)).thenReturn(Optional.empty());
        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> excluir.run(inexistente, donoId));

        UUID sistemaId = UUID.randomUUID();
        when(gateway.obterPorId(sistemaId)).thenReturn(Optional.of(TipoUsuario.reconstruir(sistemaId, "CLIENTE", true)));
        assertThrows(TipoUsuarioSistemaProtegidoException.class, () -> excluir.run(sistemaId, donoId));
        verify(gateway, never()).excluir(sistemaId);
    }
}
