package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.core.controller.TipoUsuarioController;
import com.czo.restaurantes_api.core.dto.AtualizarTipoUsuarioInputDto;
import com.czo.restaurantes_api.core.dto.CriarTipoUsuarioInputDto;
import com.czo.restaurantes_api.core.dto.ListaTiposUsuarioOutputDto;
import com.czo.restaurantes_api.core.dto.TipoUsuarioOutputDto;
import com.czo.restaurantes_api.infra.web.json.AtualizarTipoUsuarioJson;
import com.czo.restaurantes_api.infra.web.json.CriarTipoUsuarioJson;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/tipo-usuario")
public class TipoUsuarioApi {
    private final TipoUsuarioController controller;

    public TipoUsuarioApi(TipoUsuarioController controller) {
        this.controller = controller;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoUsuarioOutputDto criar(@Valid @RequestBody CriarTipoUsuarioJson json) {
        return controller.criar(new CriarTipoUsuarioInputDto(json.nomeTipo()));
    }

    @GetMapping
    public ListaTiposUsuarioOutputDto listar() {
        return controller.listar();
    }

    @GetMapping("/{id}")
    public TipoUsuarioOutputDto buscar(@PathVariable UUID id) {
        return controller.buscar(id);
    }

    @PutMapping("/{id}")
    public TipoUsuarioOutputDto atualizar(@PathVariable UUID id,
                                          @Valid @RequestBody AtualizarTipoUsuarioJson json) {
        return controller.atualizar(id, new AtualizarTipoUsuarioInputDto(json.nomeTipo()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {
        controller.excluir(id);
    }
}
