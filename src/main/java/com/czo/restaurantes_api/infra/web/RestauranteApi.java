package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.core.controller.RestauranteController;
import com.czo.restaurantes_api.core.dto.*;
import com.czo.restaurantes_api.core.dto.restaurante.AtualizarRestauranteInputDto;
import com.czo.restaurantes_api.core.dto.restaurante.CriarRestauranteInputDto;
import com.czo.restaurantes_api.core.dto.restaurante.ListaRestaurantesOutputDto;
import com.czo.restaurantes_api.core.dto.restaurante.RestauranteOutputDto;
import com.czo.restaurantes_api.infra.web.json.AtualizarRestauranteJson;
import com.czo.restaurantes_api.infra.web.json.CriarRestauranteJson;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public RestauranteOutputDto criar(@Valid @RequestBody CriarRestauranteJson json) {

        return controller.criar(new CriarRestauranteInputDto(json.nome(), toEnderecoInput(json.endereco()),
                json.tipoCozinha(), json.horarioFuncionamento(), json.donoId()));
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
                                           @Valid @RequestBody AtualizarRestauranteJson json) {

        return controller.atualizar(id, new AtualizarRestauranteInputDto(
                json.nome(), toEnderecoInput(json.endereco()), json.tipoCozinha(),
                json.horarioFuncionamento(), json.donoId()));
    }

    private EnderecoInputDto toEnderecoInput(com.czo.restaurantes_api.infra.web.json.EnderecoJson endereco) {

        return new EnderecoInputDto(endereco.rua(), endereco.numero(), endereco.bairro(),
                endereco.cidade(), endereco.estado(), endereco.cep());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID id) {

        controller.excluir(id);
    }
}
