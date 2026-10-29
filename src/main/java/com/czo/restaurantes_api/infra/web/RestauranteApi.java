package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.core.controller.RestauranteController;
import com.czo.restaurantes_api.core.dto.*;
import com.czo.restaurantes_api.core.dto.restaurante.*;
import com.czo.restaurantes_api.infra.security.UsuarioAutenticado;
import com.czo.restaurantes_api.infra.web.json.AtualizarRestauranteJson;
import com.czo.restaurantes_api.infra.web.json.CriarRestauranteJson;
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
@RequestMapping("/v1/restaurantes")
@Tag(name = "Restaurantes")
public class RestauranteApi {

    private final RestauranteController controller;

    public RestauranteApi(RestauranteController controller) {

        this.controller = controller;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastrar restaurante",
            description = "Cria um restaurante e define como proprietário o usuário autenticado. "
                    + "Não informe o ID do proprietário no corpo; somente usuários do tipo DONO podem cadastrar.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante criado.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Corpo inválido ou campos obrigatórios ausentes."),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "403", description = "O usuário autenticado não tem perfil DONO.")
    })
    public RestauranteOutputInsertDto criar(@Valid @RequestBody CriarRestauranteJson json,
                                            @AuthenticationPrincipal UsuarioAutenticado usuario) {

        return controller.criar(new CriarRestauranteInputDto(json.nome(), toEnderecoInput(json.endereco()),
                json.tipoCozinha(), json.horarioFuncionamento()), usuario.getId());
    }

    @GetMapping
    @Operation(summary = "Listar restaurantes", description = "Retorna a lista de restaurantes cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada.", useReturnTypeSchema = true)
    })
    public ListaRestaurantesOutputDto listar() {

        return controller.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante", description = "Busca um restaurante pelo UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.")
    })
    public RestauranteOutputDto buscar(@PathVariable UUID id) {

        return controller.buscar(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar restaurante",
            description = "Atualiza os dados do restaurante. Somente o usuário autenticado que é seu proprietário pode alterá-lo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Corpo inválido ou campos obrigatórios ausentes."),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "403", description = "O usuário autenticado não é o proprietário."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.")
    })
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
    @Operation(summary = "Excluir restaurante",
            description = "Exclui o restaurante. Somente o usuário autenticado que é seu proprietário pode removê-lo.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante excluído; resposta sem corpo."),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "403", description = "O usuário autenticado não é o proprietário."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.")
    })
    public void excluir(@PathVariable UUID id, @AuthenticationPrincipal UsuarioAutenticado usuario) {

        controller.excluir(id, usuario.getId());
    }
}
