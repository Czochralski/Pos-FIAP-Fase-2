package com.czo.restaurantes_api.core.usecase;

import com.czo.restaurantes_api.TestFixtures;
import com.czo.restaurantes_api.core.dto.EnderecoInputDto;
import com.czo.restaurantes_api.core.dto.restaurante.AtualizarRestauranteInputDto;
import com.czo.restaurantes_api.core.dto.restaurante.CriarRestauranteInputDto;
import com.czo.restaurantes_api.core.exception.DonoRestauranteInvalidoException;
import com.czo.restaurantes_api.core.exception.RestauranteNaoEncontradoException;
import com.czo.restaurantes_api.core.exception.UsuarioSemPermissaoAtualizarRestauranteException;
import com.czo.restaurantes_api.core.exception.UsuarioSemPermissaoCriarRestauranteException;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;
import com.czo.restaurantes_api.core.usecase.restaurante.*;
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
class RestauranteUsecasesTest {

    @Mock RestauranteGateway gateway;
    @Mock UsuarioGateway usuarioGateway;

    private EnderecoInputDto enderecoInput() {
        return new EnderecoInputDto("Rua A", "10", "Centro", "Recife", "PE", "50000-000");
    }

    @Test
    void criaRestauranteAssociadoAoDonoAutenticado() {
        when(usuarioGateway.usuarioDono(TestFixtures.OWNER_ID)).thenReturn(true);
        when(gateway.criar(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var criado = CriarRestauranteUsecase.create(gateway, usuarioGateway).run(
                new CriarRestauranteInputDto(" Central ", enderecoInput(), " Brasileira ", " 10-22 "),
                TestFixtures.OWNER_ID);

        assertNotNull(criado.id());
        assertEquals("Central", criado.nome());
        assertEquals("PE", criado.endereco().estado());
        assertEquals("Brasileira", criado.tipoCozinha());
        assertEquals("10-22", criado.horarioFuncionamento());
        verify(gateway).criar(argThat(r -> r.getDonoId().equals(TestFixtures.OWNER_ID)));
    }

    @Test
    void criacaoRejeitaClienteSemSalvar() {
        when(usuarioGateway.usuarioDono(TestFixtures.SECOND_OWNER_ID)).thenReturn(false);
        assertThrows(UsuarioSemPermissaoCriarRestauranteException.class,
                () -> CriarRestauranteUsecase.create(gateway, usuarioGateway)
                        .run(new CriarRestauranteInputDto("R", enderecoInput(), "C", "H"), TestFixtures.SECOND_OWNER_ID));
        verifyNoInteractions(gateway);
    }

    @Test
    void listaBuscaEInformaQuandoRestauranteNaoExiste() {
        RestauranteGateway readGateway = gateway;
        when(readGateway.obterTodos()).thenReturn(List.of(TestFixtures.restaurante()));
        when(readGateway.obterPorId(TestFixtures.RESTAURANT_ID)).thenReturn(Optional.of(TestFixtures.restaurante()));

        assertEquals("Restaurante Teste", ListarRestaurantesUsecase.create(gateway).run().restaurantes().getFirst().nome());
        assertEquals("Recife", BuscarRestauranteUsecase.create(gateway).run(TestFixtures.RESTAURANT_ID).endereco().cidade());
        assertThrows(RestauranteNaoEncontradoException.class,
                () -> BuscarRestauranteUsecase.create(gateway).run(UUID.randomUUID()));
    }

    @Test
    void atualizaSomenteQuandoSolicitanteEProprietario() {
        when(gateway.obterPorId(TestFixtures.RESTAURANT_ID)).thenReturn(Optional.of(TestFixtures.restaurante()));
        when(gateway.usuarioDono(TestFixtures.OWNER_ID, TestFixtures.RESTAURANT_ID)).thenReturn(true);
        when(gateway.atualizar(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var atualizado = AtualizarRestauranteUsecase.create(gateway).run(TestFixtures.RESTAURANT_ID,
                new AtualizarRestauranteInputDto("Novo nome", enderecoInput(), "Italiana", "12-23"), TestFixtures.OWNER_ID);
        assertEquals("Novo nome", atualizado.nome());
        assertEquals("Italiana", atualizado.tipoCozinha());
        verify(gateway).atualizar(any());

        when(gateway.usuarioDono(TestFixtures.SECOND_OWNER_ID, TestFixtures.RESTAURANT_ID)).thenReturn(false);
        assertThrows(UsuarioSemPermissaoAtualizarRestauranteException.class,
                () -> AtualizarRestauranteUsecase.create(gateway).run(TestFixtures.RESTAURANT_ID,
                        new AtualizarRestauranteInputDto("Inválido", enderecoInput(), "C", "H"), TestFixtures.SECOND_OWNER_ID));
        assertThrows(RestauranteNaoEncontradoException.class,
                () -> AtualizarRestauranteUsecase.create(gateway).run(UUID.randomUUID(),
                        new AtualizarRestauranteInputDto("X", enderecoInput(), "C", "H"), TestFixtures.OWNER_ID));
    }

    @Test
    void excluirValidaExistenciaEPropriedadeAntesDeRemover() {
        when(gateway.obterPorId(TestFixtures.RESTAURANT_ID)).thenReturn(Optional.of(TestFixtures.restaurante()));
        when(usuarioGateway.usuarioDono(TestFixtures.OWNER_ID)).thenReturn(true);
        when(gateway.usuarioDono(TestFixtures.OWNER_ID, TestFixtures.RESTAURANT_ID)).thenReturn(true);

        assertDoesNotThrow(() -> ExcluirRestauranteUsecase.create(gateway, usuarioGateway)
                .run(TestFixtures.RESTAURANT_ID, TestFixtures.OWNER_ID));
        verify(gateway).excluir(TestFixtures.RESTAURANT_ID);

        when(usuarioGateway.usuarioDono(TestFixtures.SECOND_OWNER_ID)).thenReturn(false);
        assertThrows(DonoRestauranteInvalidoException.class,
                () -> ExcluirRestauranteUsecase.create(gateway, usuarioGateway)
                        .run(TestFixtures.RESTAURANT_ID, TestFixtures.SECOND_OWNER_ID));

        when(usuarioGateway.usuarioDono(TestFixtures.SECOND_OWNER_ID)).thenReturn(true);
        when(gateway.usuarioDono(TestFixtures.SECOND_OWNER_ID, TestFixtures.RESTAURANT_ID)).thenReturn(false);
        assertThrows(DonoRestauranteInvalidoException.class,
                () -> ExcluirRestauranteUsecase.create(gateway, usuarioGateway)
                        .run(TestFixtures.RESTAURANT_ID, TestFixtures.SECOND_OWNER_ID));
        assertThrows(RestauranteNaoEncontradoException.class,
                () -> ExcluirRestauranteUsecase.create(gateway, usuarioGateway)
                        .run(UUID.randomUUID(), TestFixtures.OWNER_ID));
    }
}
