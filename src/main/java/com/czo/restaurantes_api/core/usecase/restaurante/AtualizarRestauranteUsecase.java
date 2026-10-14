package com.czo.restaurantes_api.core.usecase.restaurante;

import com.czo.restaurantes_api.core.domain.Endereco;
import com.czo.restaurantes_api.core.domain.Restaurante;
import com.czo.restaurantes_api.core.dto.restaurante.AtualizarRestauranteInputDto;
import com.czo.restaurantes_api.core.dto.restaurante.RestauranteOutputDto;
import com.czo.restaurantes_api.core.exception.DonoRestauranteInvalidoException;
import com.czo.restaurantes_api.core.exception.RestauranteNaoEncontradoException;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;

import java.util.UUID;

public class AtualizarRestauranteUsecase {

    private final RestauranteGateway restauranteGateway;
    private final UsuarioGateway usuarioGateway;

    private AtualizarRestauranteUsecase(RestauranteGateway restauranteGateway,
                                        UsuarioGateway usuarioGateway) {

        this.restauranteGateway = restauranteGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public static AtualizarRestauranteUsecase create(RestauranteGateway restauranteGateway,
                                                     UsuarioGateway usuarioGateway) {

        return new AtualizarRestauranteUsecase(restauranteGateway, usuarioGateway);
    }

    public RestauranteOutputDto run(UUID id, AtualizarRestauranteInputDto input) {

        Restaurante restaurante = restauranteGateway.obterPorId(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));

        if (!usuarioGateway.usuarioDono(input.donoId())) {
            throw new DonoRestauranteInvalidoException(input.donoId());
        }

        var enderecoInput = input.endereco();

        Endereco endereco = Endereco.criar(enderecoInput.rua(), enderecoInput.numero(), enderecoInput.bairro(),
                enderecoInput.cidade(), enderecoInput.estado(), enderecoInput.cep());

        restaurante.atualizar(input.nome(), endereco, input.tipoCozinha(),
                input.horarioFuncionamento(), input.donoId());

        Restaurante salvo = restauranteGateway.atualizar(restaurante);

        return RestauranteOutputMapper.toOutput(salvo);
    }
}
