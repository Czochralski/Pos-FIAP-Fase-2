package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.core.controller.RestauranteController;
import com.czo.restaurantes_api.core.dto.*;
import com.czo.restaurantes_api.core.dto.restaurante.*;
import com.czo.restaurantes_api.infra.security.UsuarioAutenticado;
import com.czo.restaurantes_api.infra.web.json.AtualizarRestauranteJson;
import com.czo.restaurantes_api.infra.web.json.CriarRestauranteJson;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/restaurantes")
public class RestauranteApi {

    private final RestauranteController controller;

    public RestauranteApi(RestauranteController controller) {

        this.controller = controller;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteOutputInsertDto criar(@Valid @RequestBody CriarRestauranteJson json,
                                            @AuthenticationPrincipal UsuarioAutenticado usuario) {

        return controller.criar(new CriarRestauranteInputDto(json.nome(), toEnderecoInput(json.endereco()),
                json.tipoCozinha(), json.horarioFuncionamento()), usuario.getId());
    }

    @GetMapping
    public ListaRestaurantesOutputDto listar() {

        return controller.listar();
    }

    @GetMapping("/{id}")
    public RestauranteOutputDto buscar(@PathVariable UUID id) {

        return controller.buscar(id);
    }

    @PutMapping("/{id}")
    public RestauranteOutputDto atualizar(@PathVariable UUID id,
                                          @Valid @RequestBody AtualizarRestauranteJson json,
                                          @AuthenticationPrincipal UsuarioAutenticado usuario) {

        return controller.atualizar(id, new AtualizarRestauranteInputDto(
                json.nome(), toEnderecoInput(json.endereco()), json.tipoCozinha(),
                json.horarioFuncionamento()), usuario.getId());
    }

    private EnderecoInputDto toEnderecoInput(com.czo.restaurantes_api.infra.web.json.EnderecoJson endereco) {

        return new EnderecoInputDto(endereco.rua(), endereco.numero(), endereco.bairro(),
                endereco.cidade(), endereco.estado(), endereco.cep());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id, @AuthenticationPrincipal UsuarioAutenticado usuario) {

        controller.excluir(id, usuario.getId());
    }
}
