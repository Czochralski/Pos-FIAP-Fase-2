package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.core.controller.ItemCardapioController;
import com.czo.restaurantes_api.core.dto.itemCardapio.*;
import com.czo.restaurantes_api.infra.security.UsuarioAutenticado;
import com.czo.restaurantes_api.infra.web.json.AtualizarItemCardapioJson;
import com.czo.restaurantes_api.infra.web.json.CriarItemCardapioJson;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/itens-cardapio")
@Tag(name = "Itens de cardápio")
public class ItemCardapioApi {

    private final ItemCardapioController controller;

    public ItemCardapioApi(ItemCardapioController controller) {
        this.controller = controller;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar item ao cardápio",
            description = "Cria um item no restaurante informado. Somente o proprietário autenticado pode alterar o cardápio.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item criado.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Corpo inválido ou campos obrigatórios ausentes."),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "403", description = "O usuário autenticado não é o proprietário do restaurante."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.")
    })
    public ItemCardapioOutputInsertDto criar(@PathVariable UUID restauranteId,
                                             @Valid @RequestBody CriarItemCardapioJson json,
                                             @AuthenticationPrincipal UsuarioAutenticado usuario) {

        return controller.criar(restauranteId, new CriarItemCardapioInputDto(json.nome(), json.descricao(), json.preco(),
                json.apenasNoRestaurante(), json.foto()), usuario.getId());
    }

    @GetMapping
    @Operation(summary = "Listar itens do cardápio", description = "Lista os itens vinculados ao restaurante informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas.")
    })
    public ListaItensCardapioOutputDto listar(@PathVariable UUID restauranteId) {

        return controller.listar(restauranteId);
    }

    @GetMapping("/{itemCardapioId}")
    @Operation(summary = "Buscar item do cardápio", description = "Busca um item pelo UUID dentro do restaurante informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "404", description = "Restaurante ou item não encontrado.")
    })
    public ItemCardapioOutputDto buscar(@PathVariable UUID restauranteId, @PathVariable UUID itemCardapioId) {

        return controller.buscar(restauranteId, itemCardapioId);
    }

    @PutMapping("/{itemCardapioId}")
    @Operation(summary = "Atualizar item do cardápio",
            description = "Atualiza um item dentro do restaurante informado. Somente o proprietário autenticado pode alterar o cardápio.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Corpo inválido ou campos obrigatórios ausentes."),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "403", description = "O usuário autenticado não é o proprietário do restaurante."),
            @ApiResponse(responseCode = "404", description = "Restaurante ou item não encontrado.")
    })
    public ItemCardapioOutputDto atualizar(@PathVariable UUID restauranteId,
                                           @PathVariable UUID itemCardapioId,
                                          @Valid @RequestBody AtualizarItemCardapioJson json,
                                          @AuthenticationPrincipal UsuarioAutenticado usuario) {

        return controller.atualizar(restauranteId, itemCardapioId, new AtualizarItemCardapioInputDto(json.nome(), json.descricao(),
                json.preco(), json.apenasNoRestaurante(), json.foto()), usuario.getId());
    }

    @DeleteMapping("/{itemCardapioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir item do cardápio",
            description = "Remove um item do restaurante informado. Somente o proprietário autenticado pode alterar o cardápio.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item excluído; resposta sem corpo."),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "403", description = "O usuário autenticado não é o proprietário do restaurante."),
            @ApiResponse(responseCode = "404", description = "Restaurante ou item não encontrado.")
    })
    public void excluir(@PathVariable UUID restauranteId,
                        @PathVariable UUID itemCardapioId,
                        @AuthenticationPrincipal UsuarioAutenticado usuario) {

        controller.excluir(restauranteId, itemCardapioId, usuario.getId());
    }
}
