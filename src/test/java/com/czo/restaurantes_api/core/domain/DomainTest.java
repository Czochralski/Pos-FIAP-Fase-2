package com.czo.restaurantes_api.core.domain;

import com.czo.restaurantes_api.TestFixtures;
import com.czo.restaurantes_api.core.exception.TipoUsuarioSistemaProtegidoException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DomainTest {

    @Test
    void enderecoCriaNormalizaCamposEAtualiza() {
        Endereco endereco = Endereco.criar(" Rua A ", " 5 ", " Centro ", " Recife ", "pe", " 50000-000 ");

        assertEquals("Rua A", endereco.getRua());
        assertEquals("5", endereco.getNumero());
        assertEquals("Centro", endereco.getBairro());
        assertEquals("Recife", endereco.getCidade());
        assertEquals("PE", endereco.getEstado());
        assertEquals("50000-000", endereco.getCep());
        assertNotNull(endereco.getId());

        UUID id = UUID.randomUUID();
        Endereco reconstruido = Endereco.reconstruir(id, " Rua B ", " 8 ", " Bairro ", " Olinda ", "pe", "53000-000");
        assertEquals(id, reconstruido.getId());
        assertEquals("PE", reconstruido.getEstado());

        endereco.atualizar(" Rua C ", " 12 ", " Norte ", " Recife ", "ce", "60000-000");
        assertEquals("Rua C", endereco.getRua());
        assertEquals("12", endereco.getNumero());
        assertEquals("Norte", endereco.getBairro());
        assertEquals("Recife", endereco.getCidade());
        assertEquals("CE", endereco.getEstado());
        assertEquals("60000-000", endereco.getCep());
    }

    @Test
    void enderecoRejeitaCadaCampoObrigatorioEEstadoInvalido() {
        assertAll(
                () -> assertEquals("Rua é obrigatória", assertThrows(IllegalArgumentException.class,
                        () -> Endereco.criar(" ", "1", "B", "C", "PE", "1")).getMessage()),
                () -> assertEquals("Número é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> Endereco.criar("R", null, "B", "C", "PE", "1")).getMessage()),
                () -> assertEquals("Bairro é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> Endereco.criar("R", "1", " ", "C", "PE", "1")).getMessage()),
                () -> assertEquals("Cidade é obrigatória", assertThrows(IllegalArgumentException.class,
                        () -> Endereco.criar("R", "1", "B", "", "PE", "1")).getMessage()),
                () -> assertEquals("Estado é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> Endereco.criar("R", "1", "B", "C", null, "1")).getMessage()),
                () -> assertEquals("CEP é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> Endereco.criar("R", "1", "B", "C", "PE", " ")).getMessage()),
                () -> assertEquals("Estado deve ter 2 caracteres", assertThrows(IllegalArgumentException.class,
                        () -> Endereco.criar("R", "1", "B", "C", "Pernambuco", "1")).getMessage()),
                () -> assertEquals("Id do endereço é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> Endereco.reconstruir(null, "R", "1", "B", "C", "PE", "1")).getMessage())
        );
    }

    @Test
    void restauranteCriaReconstruiAtualizaEValidaCampos() {
        Restaurante restaurante = Restaurante.criar(" Restaurante ", TestFixtures.endereco(),
                " Brasileira ", " 10-22 ", TestFixtures.OWNER_ID);
        assertNotNull(restaurante.getId());
        assertEquals("Restaurante", restaurante.getNome());
        assertEquals("Brasileira", restaurante.getTipoCozinha());
        assertEquals("10-22", restaurante.getHorarioFuncionamento());

        UUID id = UUID.randomUUID();
        assertEquals(id, Restaurante.reconstruir(id, "R", TestFixtures.endereco(), "C", "H",
                TestFixtures.OWNER_ID).getId());
        restaurante.atualizar(" Novo ", TestFixtures.endereco(), " Italiana ", " 9-21 ", TestFixtures.SECOND_OWNER_ID);
        assertEquals("Novo", restaurante.getNome());
        assertEquals("Italiana", restaurante.getTipoCozinha());
        assertEquals("9-21", restaurante.getHorarioFuncionamento());
        assertEquals(TestFixtures.SECOND_OWNER_ID, restaurante.getDonoId());

        assertAll(
                () -> assertEquals("Id do restaurante é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> Restaurante.reconstruir(null, "R", TestFixtures.endereco(), "C", "H", TestFixtures.OWNER_ID)).getMessage()),
                () -> assertEquals("Nome do restaurante é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> Restaurante.criar(" ", TestFixtures.endereco(), "C", "H", TestFixtures.OWNER_ID)).getMessage()),
                () -> assertEquals("Endereço do restaurante é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> Restaurante.criar("R", null, "C", "H", TestFixtures.OWNER_ID)).getMessage()),
                () -> assertEquals("Tipo de cozinha é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> Restaurante.criar("R", TestFixtures.endereco(), " ", "H", TestFixtures.OWNER_ID)).getMessage()),
                () -> assertEquals("Horário de funcionamento é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> Restaurante.criar("R", TestFixtures.endereco(), "C", null, TestFixtures.OWNER_ID)).getMessage()),
                () -> assertEquals("Dono do restaurante é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> Restaurante.criar("R", TestFixtures.endereco(), "C", "H", null)).getMessage())
        );
    }

    @Test
    void itemCardapioCriaReconstruiAtualizaEValidaCampos() {
        ItemCardapio item = ItemCardapio.criar(" Prato ", " Descrição ", new BigDecimal("4.00"),
                true, " foto ", TestFixtures.RESTAURANT_ID);
        assertNotNull(item.getId());
        assertEquals("Prato", item.getNome());
        assertEquals("Descrição", item.getDescricao());
        assertEquals("foto", item.getFoto());
        assertTrue(item.isApenasNoRestaurante());

        UUID id = UUID.randomUUID();
        assertEquals(id, ItemCardapio.reconstruir(id, "R", "D", BigDecimal.ONE, false, "F",
                TestFixtures.RESTAURANT_ID).getId());
        item.atualizar(" Novo prato ", " Nova descrição ", new BigDecimal("8.50"), false, " nova foto ");
        assertEquals("Novo prato", item.getNome());
        assertEquals("Nova descrição", item.getDescricao());
        assertEquals(new BigDecimal("8.50"), item.getPreco());
        assertFalse(item.isApenasNoRestaurante());
        assertEquals("nova foto", item.getFoto());

        assertAll(
                () -> assertEquals("Id do Item é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> ItemCardapio.reconstruir(null, "R", "D", BigDecimal.ONE, false, "F", TestFixtures.RESTAURANT_ID)).getMessage()),
                () -> assertEquals("Nome é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> ItemCardapio.criar(" ", "D", BigDecimal.ONE, false, "F", TestFixtures.RESTAURANT_ID)).getMessage()),
                () -> assertEquals("Descrição é obrigatória", assertThrows(IllegalArgumentException.class,
                        () -> ItemCardapio.criar("N", null, BigDecimal.ONE, false, "F", TestFixtures.RESTAURANT_ID)).getMessage()),
                () -> assertEquals("Preço é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> ItemCardapio.criar("N", "D", null, false, "F", TestFixtures.RESTAURANT_ID)).getMessage()),
                () -> assertEquals("Preço deve ser maior que zero", assertThrows(IllegalArgumentException.class,
                        () -> ItemCardapio.criar("N", "D", BigDecimal.ZERO, false, "F", TestFixtures.RESTAURANT_ID)).getMessage()),
                () -> assertEquals("Caminho da foto é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> ItemCardapio.criar("N", "D", BigDecimal.ONE, false, " ", TestFixtures.RESTAURANT_ID)).getMessage()),
                () -> assertEquals("Restaurante do item é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> ItemCardapio.criar("N", "D", BigDecimal.ONE, false, "F", null)).getMessage())
        );
    }

    @Test
    void tipoUsuarioValidaNomeEProtegeTiposDoSistema() {
        TipoUsuario tipo = TipoUsuario.criar(" GERENTE ");
        assertNotNull(tipo.getId());
        assertEquals("GERENTE", tipo.getNomeTipo());
        assertFalse(tipo.isSistema());

        UUID id = UUID.randomUUID();
        TipoUsuario reconstruido = TipoUsuario.reconstruir(id, " DONO ", true);
        assertEquals(id, reconstruido.getId());
        assertEquals("DONO", reconstruido.getNomeTipo());
        assertTrue(reconstruido.isSistema());

        tipo.atualizarNome(" GESTOR ");
        assertEquals("GESTOR", tipo.getNomeTipo());
        assertThrows(TipoUsuarioSistemaProtegidoException.class, () -> reconstruido.atualizarNome("OUTRO"));
        assertAll(
                () -> assertEquals("Nome do tipo de usuário é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> TipoUsuario.criar(" ")).getMessage()),
                () -> assertEquals("Nome do tipo de usuário é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> TipoUsuario.reconstruir(UUID.randomUUID(), null, false)).getMessage()),
                () -> assertEquals("Id do tipo de usuário é obrigatório", assertThrows(IllegalArgumentException.class,
                        () -> TipoUsuario.reconstruir(null, "DONO", true)).getMessage())
        );
    }
}
