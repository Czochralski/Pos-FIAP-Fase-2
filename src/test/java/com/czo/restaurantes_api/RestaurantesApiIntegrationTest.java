package com.czo.restaurantes_api;

import com.czo.restaurantes_api.infra.database.jpa.repository.ItemCardapioRepository;
import com.czo.restaurantes_api.infra.database.jpa.repository.RestauranteRepository;
import com.czo.restaurantes_api.infra.database.jpa.repository.TipoUsuarioRepository;
import com.czo.restaurantes_api.infra.database.jpa.entity.TipoUsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:restaurantes-test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=validate",
        "spring.flyway.enabled=true"
})
@AutoConfigureMockMvc
class RestaurantesApiIntegrationTest {

    private static final String DONO = "dono";
    private static final String DONO2 = "dono2";
    private static final String CLIENTE = "cliente";
    private static final String SENHA = "123456";
    private static final UUID TIPO_CLIENTE_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID TIPO_DONO_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    @Autowired
    MockMvc mvc;

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    ItemCardapioRepository itemCardapioRepository;

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    @BeforeEach
    void limparDadosDeTestesAnteriores() {
        itemCardapioRepository.deleteAll();
        restauranteRepository.deleteAll();
        tipoUsuarioRepository.findAll().stream()
                .filter(tipo -> !tipo.isSistema())
                .map(TipoUsuarioEntity::getId)
                .forEach(tipoUsuarioRepository::deleteById);
    }

    @Test
    void executaFluxoAutenticacaoRestauranteECardapioComPersistencia() throws Exception {
        mvc.perform(get("/v1/restaurantes"))
                .andExpect(status().isUnauthorized());

        String restauranteJson = """
                {"nome":"Restaurante de Integração","endereco":{"rua":"Rua Teste","numero":"10",\
                "bairro":"Centro","cidade":"Recife","estado":"PE","cep":"50000-000"},\
                "tipoCozinha":"Brasileira","horarioFuncionamento":"10:00 às 22:00"}
                """.replace("\\\n", "");
        mvc.perform(post("/v1/restaurantes").with(httpBasic(CLIENTE, SENHA))
                        .contentType("application/json").content(restauranteJson))
                .andExpect(status().isForbidden());

        mvc.perform(get("/v1/auth/me").with(httpBasic(DONO, SENHA)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value(DONO))
                .andExpect(jsonPath("$.tipoUsuario").value("DONO"));

        String restauranteResponse = mvc.perform(post("/v1/restaurantes")
                        .with(httpBasic(DONO, SENHA)).contentType("application/json").content(restauranteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Restaurante de Integração"))
                .andReturn().getResponse().getContentAsString();
        UUID restauranteId = extrairUuid(restauranteResponse);

        mvc.perform(get("/v1/restaurantes/{id}", restauranteId).with(httpBasic(CLIENTE, SENHA)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.endereco.estado").value("PE"));
        mvc.perform(get("/v1/restaurantes").with(httpBasic(CLIENTE, SENHA)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantes[0].nome").value("Restaurante de Integração"));

        String updateRestaurante = """
                {"nome":"Restaurante Atualizado","endereco":{"rua":"Rua Nova","numero":"20",\
                "bairro":"Boa Vista","cidade":"Recife","estado":"PE","cep":"50000-111"},\
                "tipoCozinha":"Nordestina","horarioFuncionamento":"11:00 às 23:00"}
                """.replace("\\\n", "");
        mvc.perform(put("/v1/restaurantes/{id}", restauranteId).with(httpBasic(DONO2, SENHA))
                        .contentType("application/json").content(updateRestaurante))
                .andExpect(status().isForbidden());
        mvc.perform(put("/v1/restaurantes/{id}", restauranteId).with(httpBasic(DONO, SENHA))
                        .contentType("application/json").content(updateRestaurante))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Restaurante Atualizado"));

        String itemJson = """
                {"nome":"Baião de dois","descricao":"Prato da casa","preco":32.50,\
                "apenasNoRestaurante":false,"foto":"https://exemplo.test/baiao.jpg"}
                """.replace("\\\n", "");
        mvc.perform(post("/v1/restaurantes/{id}/itens-cardapio", restauranteId)
                        .with(httpBasic(CLIENTE, SENHA)).contentType("application/json").content(itemJson))
                .andExpect(status().isForbidden());
        String itemResponse = mvc.perform(post("/v1/restaurantes/{id}/itens-cardapio", restauranteId)
                        .with(httpBasic(DONO, SENHA)).contentType("application/json").content(itemJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Baião de dois"))
                .andReturn().getResponse().getContentAsString();
        UUID itemId = extrairUuid(itemResponse);

        mvc.perform(get("/v1/restaurantes/{id}/itens-cardapio", restauranteId).with(httpBasic(CLIENTE, SENHA)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itens[0].nome").value("Baião de dois"));
        mvc.perform(get("/v1/restaurantes/{restauranteId}/itens-cardapio/{itemId}", restauranteId, itemId)
                        .with(httpBasic(CLIENTE, SENHA)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preco").value(32.50));

        String updateItem = """
                {"nome":"Baião Especial","descricao":"Versão atualizada","preco":36.00,\
                "apenasNoRestaurante":true,"foto":"https://exemplo.test/baiao-especial.jpg"}
                """.replace("\\\n", "");
        mvc.perform(put("/v1/restaurantes/{restauranteId}/itens-cardapio/{itemId}", restauranteId, itemId)
                        .with(httpBasic(DONO2, SENHA)).contentType("application/json").content(updateItem))
                .andExpect(status().isForbidden());
        mvc.perform(put("/v1/restaurantes/{restauranteId}/itens-cardapio/{itemId}", restauranteId, itemId)
                        .with(httpBasic(DONO, SENHA)).contentType("application/json").content(updateItem))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Baião Especial"));
        mvc.perform(delete("/v1/restaurantes/{restauranteId}/itens-cardapio/{itemId}", restauranteId, itemId)
                        .with(httpBasic(DONO2, SENHA)))
                .andExpect(status().isForbidden());
        mvc.perform(delete("/v1/restaurantes/{restauranteId}/itens-cardapio/{itemId}", restauranteId, itemId)
                        .with(httpBasic(DONO, SENHA)))
                .andExpect(status().isNoContent());
        mvc.perform(get("/v1/restaurantes/{restauranteId}/itens-cardapio/{itemId}", restauranteId, itemId)
                        .with(httpBasic(CLIENTE, SENHA)))
                .andExpect(status().isNotFound());

        String itemForCascade = mvc.perform(post("/v1/restaurantes/{id}/itens-cardapio", restauranteId)
                        .with(httpBasic(DONO, SENHA)).contentType("application/json").content(itemJson))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        UUID cascadeItemId = extrairUuid(itemForCascade);
        mvc.perform(delete("/v1/restaurantes/{id}", restauranteId).with(httpBasic(DONO2, SENHA)))
                .andExpect(status().isForbidden());
        mvc.perform(delete("/v1/restaurantes/{id}", restauranteId).with(httpBasic(DONO, SENHA)))
                .andExpect(status().isNoContent());
        restauranteRepository.flush();
        assertFalse(restauranteRepository.existsById(restauranteId));
        assertFalse(itemCardapioRepository.existsById(cascadeItemId));
    }

    @Test
    void executaCrudDeTipoDeUsuarioEProtegeTiposDoSistema() throws Exception {
        mvc.perform(post("/v1/tipo-usuario").with(httpBasic(CLIENTE, SENHA))
                        .contentType("application/json").content("{\"nomeTipo\":\"BLOQUEADO\"}"))
                .andExpect(status().isForbidden());

        String criadoResponse = mvc.perform(post("/v1/tipo-usuario").with(httpBasic(DONO, SENHA))
                        .contentType("application/json").content("{\"nomeTipo\":\"GERENTE\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeTipo").value("GERENTE"))
                .andExpect(jsonPath("$.sistema").value(false))
                .andReturn().getResponse().getContentAsString();
        UUID tipoId = extrairUuid(criadoResponse);

        mvc.perform(get("/v1/tipo-usuario/{id}", tipoId).with(httpBasic(CLIENTE, SENHA)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.nomeTipo").value("GERENTE"));
        mvc.perform(get("/v1/tipo-usuario").with(httpBasic(CLIENTE, SENHA)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.tipos.length()").value(3));
        mvc.perform(post("/v1/tipo-usuario").with(httpBasic(DONO, SENHA))
                        .contentType("application/json").content("{\"nomeTipo\":\"GERENTE\"}"))
                .andExpect(status().isConflict());

        mvc.perform(put("/v1/tipo-usuario/{id}", tipoId).with(httpBasic(DONO, SENHA))
                        .contentType("application/json").content("{\"nomeTipo\":\"GESTOR\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.nomeTipo").value("GESTOR"));
        mvc.perform(put("/v1/tipo-usuario/{id}", TIPO_CLIENTE_ID).with(httpBasic(DONO, SENHA))
                        .contentType("application/json").content("{\"nomeTipo\":\"VISITANTE\"}"))
                .andExpect(status().isConflict());
        mvc.perform(delete("/v1/tipo-usuario/{id}", TIPO_DONO_ID).with(httpBasic(DONO, SENHA)))
                .andExpect(status().isConflict());
        mvc.perform(delete("/v1/tipo-usuario/{id}", tipoId).with(httpBasic(DONO, SENHA)))
                .andExpect(status().isNoContent());
        mvc.perform(get("/v1/tipo-usuario/{id}", tipoId).with(httpBasic(CLIENTE, SENHA)))
                .andExpect(status().isNotFound());
        mvc.perform(get("/v1/tipo-usuario/{id}", UUID.randomUUID()).with(httpBasic(CLIENTE, SENHA)))
                .andExpect(status().isNotFound());
        mvc.perform(post("/v1/tipo-usuario").contentType("application/json")
                        .content("{\"nomeTipo\":\"SEM LOGIN\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void mapeiaErrosDeValidacaoEEntradaDeDominioParaHttp() throws Exception {
        mvc.perform(post("/v1/tipo-usuario").with(httpBasic(DONO, SENHA))
                        .contentType("application/json").content("{\"nomeTipo\":\" \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Erro de validação"));

        String enderecoInvalido = """
                {"nome":"Restaurante Inválido","endereco":{"rua":"Rua","numero":"1",\
                "bairro":"Centro","cidade":"Recife","estado":"Pernambuco","cep":"50000-000"},\
                "tipoCozinha":"Brasileira","horarioFuncionamento":"10-22"}
                """.replace("\\\n", "");
        mvc.perform(post("/v1/restaurantes").with(httpBasic(DONO, SENHA))
                        .contentType("application/json").content(enderecoInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Estado deve ter 2 caracteres"));

        mvc.perform(get("/v1/restaurantes/{id}", UUID.randomUUID()).with(httpBasic(CLIENTE, SENHA)))
                .andExpect(status().isNotFound());
        mvc.perform(get("/v1/restaurantes/{restauranteId}/itens-cardapio/{itemId}", UUID.randomUUID(), UUID.randomUUID())
                        .with(httpBasic(CLIENTE, SENHA)))
                .andExpect(status().isNotFound());
    }

    private UUID extrairUuid(String json) {
        Matcher matcher = Pattern.compile("\\\"id\\\"\\s*:\\s*\\\"([0-9a-fA-F-]{36})\\\"").matcher(json);
        if (!matcher.find()) {
            throw new AssertionError("A resposta não contém o campo id: " + json);
        }
        return UUID.fromString(matcher.group(1));
    }
}
