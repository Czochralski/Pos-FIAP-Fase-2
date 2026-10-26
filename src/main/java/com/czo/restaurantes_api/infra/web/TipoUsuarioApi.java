package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.core.controller.TipoUsuarioController;
import com.czo.restaurantes_api.core.dto.tipoUsuario.*;
import com.czo.restaurantes_api.infra.security.UsuarioAutenticado;
import com.czo.restaurantes_api.infra.web.json.AtualizarTipoUsuarioJson;
import com.czo.restaurantes_api.infra.web.json.CriarTipoUsuarioJson;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public TipoUsuarioOutputInsertDto criar(@Valid @RequestBody CriarTipoUsuarioJson json,
                                            @AuthenticationPrincipal UsuarioAutenticado usuario) {

        return controller.criar(new CriarTipoUsuarioInputDto(json.nomeTipo()), usuario.getId());
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
                                          @Valid @RequestBody AtualizarTipoUsuarioJson json,
                                          @AuthenticationPrincipal UsuarioAutenticado usuario) {

        return controller.atualizar(id, new AtualizarTipoUsuarioInputDto(json.nomeTipo()), usuario.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id,
                        @AuthenticationPrincipal UsuarioAutenticado usuario) {

        controller.excluir(id, usuario.getId());
    }
}
