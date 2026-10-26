package com.czo.restaurantes_api.core.usecase.restaurante;

import com.czo.restaurantes_api.core.domain.Endereco;
import com.czo.restaurantes_api.core.domain.Restaurante;
import com.czo.restaurantes_api.core.dto.restaurante.CriarRestauranteInputDto;
import com.czo.restaurantes_api.core.dto.restaurante.RestauranteOutputInsertDto;
import com.czo.restaurantes_api.core.exception.UsuarioSemPermissaoCriarRestauranteException;
import com.czo.restaurantes_api.core.gateway.RestauranteGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;

import java.util.UUID;

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

    public RestauranteOutputInsertDto run(CriarRestauranteInputDto input, UUID usuarioId) {

        if (!usuarioGateway.usuarioDono(usuarioId)) {
            throw new UsuarioSemPermissaoCriarRestauranteException();
        }

        var enderecoInput = input.endereco();

        Endereco endereco = Endereco.criar(enderecoInput.rua(), enderecoInput.numero(), enderecoInput.bairro(),
                enderecoInput.cidade(), enderecoInput.estado(), enderecoInput.cep());

        Restaurante restaurante = Restaurante.criar(input.nome(), endereco, input.tipoCozinha(),
                input.horarioFuncionamento(), usuarioId);

        Restaurante salvo = restauranteGateway.criar(restaurante);

        return RestauranteOutputMapper.toOutputInsert(salvo);
    }
}
