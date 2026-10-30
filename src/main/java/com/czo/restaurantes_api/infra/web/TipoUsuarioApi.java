package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.core.controller.TipoUsuarioController;
import com.czo.restaurantes_api.core.dto.tipoUsuario.*;
import com.czo.restaurantes_api.infra.security.UsuarioAutenticado;
import com.czo.restaurantes_api.infra.web.json.AtualizarTipoUsuarioJson;
import com.czo.restaurantes_api.infra.web.json.CriarTipoUsuarioJson;
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
@RequestMapping("/v1/tipo-usuario")
@Tag(name = "Tipos de usuário")
public class TipoUsuarioApi {

    private final TipoUsuarioController controller;

    public TipoUsuarioApi(TipoUsuarioController controller) {

        this.controller = controller;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar tipo de usuário", description = "Cria um tipo personalizado. Somente usuários DONO podem executar esta operação.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de usuário criado.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Nome inválido ou ausente."),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "403", description = "O usuário autenticado não tem perfil DONO."),
            @ApiResponse(responseCode = "409", description = "Já existe um tipo com esse nome.")
    })
    public TipoUsuarioOutputInsertDto criar(@Valid @RequestBody CriarTipoUsuarioJson json,
                                            @AuthenticationPrincipal UsuarioAutenticado usuario) {

        return controller.criar(new CriarTipoUsuarioInputDto(json.nomeTipo()), usuario.getId());
    }

    @GetMapping
    @Operation(summary = "Listar tipos de usuário", description = "Retorna os tipos cadastrados, incluindo os tipos de sistema protegidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas.")
    })
    public ListaTiposUsuarioOutputDto listar() {

        return controller.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de usuário", description = "Busca um tipo de usuário pelo UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo encontrado.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado.")
    })
    public TipoUsuarioOutputDto buscar(@PathVariable UUID id) {

        return controller.buscar(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Renomear tipo de usuário",
            description = "Renomeia um tipo personalizado. Somente usuários DONO podem executar esta operação; tipos de sistema CLIENTE e DONO são protegidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo atualizado.", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Nome inválido ou ausente."),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "403", description = "O usuário autenticado não tem perfil DONO."),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado."),
            @ApiResponse(responseCode = "409", description = "Nome já utilizado ou tipo de sistema protegido.")
    })
    public TipoUsuarioOutputDto atualizar(@PathVariable UUID id,
                                          @Valid @RequestBody AtualizarTipoUsuarioJson json,
                                          @AuthenticationPrincipal UsuarioAutenticado usuario) {

        return controller.atualizar(id, new AtualizarTipoUsuarioInputDto(json.nomeTipo()), usuario.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir tipo de usuário",
            description = "Exclui um tipo personalizado. Somente usuários DONO podem executar esta operação; tipos de sistema CLIENTE e DONO são protegidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo excluído; resposta sem corpo."),
            @ApiResponse(responseCode = "401", description = "Credenciais ausentes ou inválidas."),
            @ApiResponse(responseCode = "403", description = "O usuário autenticado não tem perfil DONO."),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado."),
            @ApiResponse(responseCode = "409", description = "Tipo de sistema protegido.")
    })
    public void excluir(@PathVariable UUID id,
                        @AuthenticationPrincipal UsuarioAutenticado usuario) {

        controller.excluir(id, usuario.getId());
    }
}
