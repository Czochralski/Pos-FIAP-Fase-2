package com.czo.restaurantes_api.core.usecase;

import com.czo.restaurantes_api.TestFixtures;
import com.czo.restaurantes_api.core.dto.itemCardapio.AtualizarItemCardapioInputDto;
import com.czo.restaurantes_api.core.dto.itemCardapio.CriarItemCardapioInputDto;
import com.czo.restaurantes_api.core.exception.ItemCardapioNaoEncontradoException;
import com.czo.restaurantes_api.core.exception.UsuarioSemPermissaoCardapioException;
import com.czo.restaurantes_api.core.gateway.ItemCardapioGateway;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;
import com.czo.restaurantes_api.core.usecase.itemCardapio.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCardapioUsecasesTest {

    @Mock ItemCardapioGateway itemGateway;
    @Mock RestauranteGateway restauranteGateway;
    @Mock UsuarioGateway usuarioGateway;

    private ValidadorItemCardapio validador() {
        return ValidadorItemCardapio.create(usuarioGateway, restauranteGateway);
    }

    private CriarItemCardapioInputDto criarInput() {
        return new CriarItemCardapioInputDto("Prato", "Descrição", new BigDecimal("25.00"), false, "foto.jpg");
    }

    private AtualizarItemCardapioInputDto atualizarInput() {
        return new AtualizarItemCardapioInputDto("Novo prato", "Nova descrição", new BigDecimal("30.00"), true, "nova.jpg");
    }

    private void permitirDono() {
        when(usuarioGateway.usuarioDono(TestFixtures.OWNER_ID)).thenReturn(true);
        when(restauranteGateway.usuarioDono(TestFixtures.OWNER_ID, TestFixtures.RESTAURANT_ID)).thenReturn(true);
    }

    @Test
    void criaItemSomenteParaDonoDoRestaurante() {
        permitirDono();
        when(itemGateway.criar(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var criado = CriarItemCardapioUsecase.create(itemGateway, validador())
                .run(TestFixtures.RESTAURANT_ID, criarInput(), TestFixtures.OWNER_ID);
        assertNotNull(criado.id());
        assertEquals("Prato", criado.nome());
        assertEquals(new BigDecimal("25.00"), criado.preco());

        when(usuarioGateway.usuarioDono(TestFixtures.SECOND_OWNER_ID)).thenReturn(false);
        assertThrows(UsuarioSemPermissaoCardapioException.class,
                () -> CriarItemCardapioUsecase.create(itemGateway, validador())
                        .run(TestFixtures.RESTAURANT_ID, criarInput(), TestFixtures.SECOND_OWNER_ID));
    }

    @Test
    void listaItensDoRestaurante() {
        when(itemGateway.obterPorRestaurante(TestFixtures.RESTAURANT_ID)).thenReturn(List.of(TestFixtures.item()));

        var lista = ListarItensCardapioUsecase.create(itemGateway).run(TestFixtures.RESTAURANT_ID);

        assertEquals(1, lista.itens().size());
        assertEquals("Prato Teste", lista.itens().getFirst().nome());
    }

    @Test
    void buscaItemSomenteNoRestauranteInformado() {
        when(itemGateway.obterPorId(TestFixtures.ITEM_ID)).thenReturn(Optional.of(TestFixtures.item()));

        var encontrado = BuscarItemCardapioUsecase.create(itemGateway)
                .run(TestFixtures.RESTAURANT_ID, TestFixtures.ITEM_ID);
        assertEquals("Prato Teste", encontrado.nome());
        assertThrows(ItemCardapioNaoEncontradoException.class,
                () -> BuscarItemCardapioUsecase.create(itemGateway).run(TestFixtures.RESTAURANT_ID, UUID.randomUUID()));
        assertThrows(ItemCardapioNaoEncontradoException.class,
                () -> BuscarItemCardapioUsecase.create(itemGateway).run(UUID.randomUUID(), TestFixtures.ITEM_ID));
    }

    @Test
    void atualizaItemVerificandoExistenciaRestauranteEProprietario() {
        when(itemGateway.obterPorId(TestFixtures.ITEM_ID)).thenReturn(Optional.of(TestFixtures.item()));
        permitirDono();
        when(itemGateway.atualizar(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var atualizado = AtualizarItemCardapioUsecase.create(itemGateway, validador())
                .run(TestFixtures.RESTAURANT_ID, TestFixtures.ITEM_ID, atualizarInput(), TestFixtures.OWNER_ID);
        assertEquals("Novo prato", atualizado.nome());
        assertEquals(new BigDecimal("30.00"), atualizado.preco());
        assertTrue(atualizado.apenasNoRestaurante());

        when(usuarioGateway.usuarioDono(TestFixtures.SECOND_OWNER_ID)).thenReturn(false);
        assertThrows(UsuarioSemPermissaoCardapioException.class,
                () -> AtualizarItemCardapioUsecase.create(itemGateway, validador())
                        .run(TestFixtures.RESTAURANT_ID, TestFixtures.ITEM_ID, atualizarInput(), TestFixtures.SECOND_OWNER_ID));
        assertThrows(ItemCardapioNaoEncontradoException.class,
                () -> AtualizarItemCardapioUsecase.create(itemGateway, validador())
                        .run(UUID.randomUUID(), TestFixtures.ITEM_ID, atualizarInput(), TestFixtures.OWNER_ID));
        assertThrows(ItemCardapioNaoEncontradoException.class,
                () -> AtualizarItemCardapioUsecase.create(itemGateway, validador())
                        .run(TestFixtures.RESTAURANT_ID, UUID.randomUUID(), atualizarInput(), TestFixtures.OWNER_ID));
    }

    @Test
    void excluiItemVerificandoExistenciaRestauranteEProprietario() {
        when(itemGateway.obterPorId(TestFixtures.ITEM_ID)).thenReturn(Optional.of(TestFixtures.item()));
        permitirDono();
        ExcluirItemCardapioUsecase excluir = ExcluirItemCardapioUsecase.create(itemGateway, validador());

        assertDoesNotThrow(() -> excluir.run(TestFixtures.RESTAURANT_ID, TestFixtures.ITEM_ID, TestFixtures.OWNER_ID));
        verify(itemGateway).excluir(TestFixtures.ITEM_ID);

        when(usuarioGateway.usuarioDono(TestFixtures.SECOND_OWNER_ID)).thenReturn(false);
        assertThrows(UsuarioSemPermissaoCardapioException.class,
                () -> excluir.run(TestFixtures.RESTAURANT_ID, TestFixtures.ITEM_ID, TestFixtures.SECOND_OWNER_ID));
        assertThrows(ItemCardapioNaoEncontradoException.class,
                () -> excluir.run(UUID.randomUUID(), TestFixtures.ITEM_ID, TestFixtures.OWNER_ID));
        assertThrows(ItemCardapioNaoEncontradoException.class,
                () -> excluir.run(TestFixtures.RESTAURANT_ID, UUID.randomUUID(), TestFixtures.OWNER_ID));
    }

    @Test
    void validadorRejeitaDonoDeOutroRestaurante() {
        when(usuarioGateway.usuarioDono(TestFixtures.OWNER_ID)).thenReturn(true);
        when(restauranteGateway.usuarioDono(TestFixtures.OWNER_ID, TestFixtures.RESTAURANT_ID)).thenReturn(false);

        assertThrows(UsuarioSemPermissaoCardapioException.class,
                () -> validador().validarDonoDoRestaurante(TestFixtures.OWNER_ID, TestFixtures.RESTAURANT_ID));
    }
}
