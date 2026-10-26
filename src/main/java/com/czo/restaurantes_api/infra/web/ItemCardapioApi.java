package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.core.controller.ItemCardapioController;
import com.czo.restaurantes_api.core.dto.itemCardapio.*;
import com.czo.restaurantes_api.infra.security.UsuarioAutenticado;
import com.czo.restaurantes_api.infra.web.json.AtualizarItemCardapioJson;
import com.czo.restaurantes_api.infra.web.json.CriarItemCardapioJson;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/itens-cardapio")
public class ItemCardapioApi {

    private final ItemCardapioController controller;

    public ItemCardapioApi(ItemCardapioController controller) {
        this.controller = controller;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemCardapioOutputInsertDto criar(@PathVariable UUID restauranteId,
                                             @Valid @RequestBody CriarItemCardapioJson json,
                                             @AuthenticationPrincipal UsuarioAutenticado usuario) {

        return controller.criar(restauranteId, new CriarItemCardapioInputDto(json.nome(), json.descricao(), json.preco(),
                json.apenasNoRestaurante(), json.foto()), usuario.getId());
    }

    @GetMapping
    public ListaItensCardapioOutputDto listar(@PathVariable UUID restauranteId) {
        return controller.listar(restauranteId);
    }

    @GetMapping("/{itemCardapioId}")
    public ItemCardapioOutputDto buscar(@PathVariable UUID restauranteId, @PathVariable UUID itemCardapioId) {
        return controller.buscar(restauranteId, itemCardapioId);
    }

    @PutMapping("/{itemCardapioId}")
    public ItemCardapioOutputDto atualizar(@PathVariable UUID restauranteId,
                                           @PathVariable UUID itemCardapioId,
                                          @Valid @RequestBody AtualizarItemCardapioJson json,
                                          @AuthenticationPrincipal UsuarioAutenticado usuario) {

        return controller.atualizar(restauranteId, itemCardapioId, new AtualizarItemCardapioInputDto(json.nome(), json.descricao(),
                json.preco(), json.apenasNoRestaurante(), json.foto()), usuario.getId());
    }

    @DeleteMapping("/{itemCardapioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID restauranteId,
                        @PathVariable UUID itemCardapioId,
                        @AuthenticationPrincipal UsuarioAutenticado usuario) {
        controller.excluir(restauranteId, itemCardapioId, usuario.getId());
    }
}
