package com.czo.restaurantes_api.core.usecase.restaurante;

import com.czo.restaurantes_api.core.domain.Endereco;
import com.czo.restaurantes_api.core.domain.Restaurante;
import com.czo.restaurantes_api.core.dto.restaurante.AtualizarRestauranteInputDto;
import com.czo.restaurantes_api.core.dto.restaurante.RestauranteOutputDto;
import com.czo.restaurantes_api.core.exception.RestauranteNaoEncontradoException;
import com.czo.restaurantes_api.core.exception.UsuarioSemPermissaoAtualizarRestauranteException;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;

import java.util.UUID;

public class AtualizarRestauranteUsecase {

    private final RestauranteGateway restauranteGateway;

    private AtualizarRestauranteUsecase(RestauranteGateway restauranteGateway) {

        this.restauranteGateway = restauranteGateway;
    }

    public static AtualizarRestauranteUsecase create(RestauranteGateway restauranteGateway) {

        return new AtualizarRestauranteUsecase(restauranteGateway);
    }

    public RestauranteOutputDto run(UUID id, AtualizarRestauranteInputDto input, UUID usuarioId) {

        Restaurante restaurante = restauranteGateway.obterPorId(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));

        if (!restauranteGateway.usuarioDono(usuarioId, restaurante.getId())){
            throw new UsuarioSemPermissaoAtualizarRestauranteException();
        }

        var enderecoInput = input.endereco();

        Endereco endereco = Endereco.criar(enderecoInput.rua(), enderecoInput.numero(), enderecoInput.bairro(),
                enderecoInput.cidade(), enderecoInput.estado(), enderecoInput.cep());

        restaurante.atualizar(input.nome(), endereco, input.tipoCozinha(),
                input.horarioFuncionamento(), usuarioId);

        Restaurante salvo = restauranteGateway.atualizar(restaurante);

        return RestauranteOutputMapper.toOutput(salvo);
    }
}
