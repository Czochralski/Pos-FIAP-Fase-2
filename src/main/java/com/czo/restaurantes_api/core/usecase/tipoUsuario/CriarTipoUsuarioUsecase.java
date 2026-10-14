package com.czo.restaurantes_api.core.usecase.tipoUsuario;

import com.czo.restaurantes_api.core.domain.TipoUsuario;
import com.czo.restaurantes_api.core.dto.tipoUsuario.CriarTipoUsuarioInputDto;
import com.czo.restaurantes_api.core.dto.tipoUsuario.TipoUsuarioOutputDto;
import com.czo.restaurantes_api.core.exception.TipoUsuarioJaExisteException;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;

public class CriarTipoUsuarioUsecase {

    private final TipoUsuarioGateway gateway;

    private CriarTipoUsuarioUsecase(TipoUsuarioGateway gateway) {

        this.gateway = gateway;
    }

    public static CriarTipoUsuarioUsecase create(TipoUsuarioGateway gateway) {

        return new CriarTipoUsuarioUsecase(gateway);
    }

    public TipoUsuarioOutputDto run(CriarTipoUsuarioInputDto input) {

        gateway.obterPorNome(input.nomeTipo()).ifPresent(tipo -> {
            throw new TipoUsuarioJaExisteException(input.nomeTipo());
        });

        TipoUsuario tipoUsuario = TipoUsuario.criar(input.nomeTipo());
        TipoUsuario salvo = gateway.criar(tipoUsuario);

        return new TipoUsuarioOutputDto(salvo.getId(), salvo.getNomeTipo());
    }
}
