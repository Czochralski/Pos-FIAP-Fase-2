package com.czo.restaurantes_api.core.usecase.restaurante;

import com.czo.restaurantes_api.core.domain.Endereco;
import com.czo.restaurantes_api.core.domain.Restaurante;
import com.czo.restaurantes_api.core.dto.restaurante.CriarRestauranteInputDto;
import com.czo.restaurantes_api.core.dto.restaurante.RestauranteOutputDto;
import com.czo.restaurantes_api.core.exception.DonoRestauranteInvalidoException;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;

public class CriarRestauranteUsecase {

    private final RestauranteGateway restauranteGateway;
    private final UsuarioGateway usuarioGateway;

    private CriarRestauranteUsecase(RestauranteGateway restauranteGateway, UsuarioGateway usuarioGateway) {

        this.restauranteGateway = restauranteGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public static CriarRestauranteUsecase create(RestauranteGateway restauranteGateway,
                                                 UsuarioGateway usuarioGateway) {

        return new CriarRestauranteUsecase(restauranteGateway, usuarioGateway);
    }

    public RestauranteOutputDto run(CriarRestauranteInputDto input) {

        if (!usuarioGateway.usuarioDono(input.donoId())) {
            throw new DonoRestauranteInvalidoException(input.donoId());
        }

        var enderecoInput = input.endereco();

        Endereco endereco = Endereco.criar(enderecoInput.rua(), enderecoInput.numero(), enderecoInput.bairro(),
                enderecoInput.cidade(), enderecoInput.estado(), enderecoInput.cep());

        Restaurante restaurante = Restaurante.criar(input.nome(), endereco, input.tipoCozinha(),
                input.horarioFuncionamento(), input.donoId());

        Restaurante salvo = restauranteGateway.criar(restaurante);

        return RestauranteOutputMapper.toOutput(salvo);
    }
}
