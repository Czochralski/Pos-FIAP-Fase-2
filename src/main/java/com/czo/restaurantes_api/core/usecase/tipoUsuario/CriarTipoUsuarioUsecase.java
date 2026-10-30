package com.czo.restaurantes_api.core.usecase.tipoUsuario;

import com.czo.restaurantes_api.core.domain.TipoUsuario;
import com.czo.restaurantes_api.core.dto.tipoUsuario.CriarTipoUsuarioInputDto;
import com.czo.restaurantes_api.core.dto.tipoUsuario.TipoUsuarioOutputDto;
import com.czo.restaurantes_api.core.dto.tipoUsuario.TipoUsuarioOutputInsertDto;
import com.czo.restaurantes_api.core.exception.DonoRestauranteInvalidoException;
import com.czo.restaurantes_api.core.exception.TipoUsuarioJaExisteException;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;

import java.util.UUID;

public class CriarTipoUsuarioUsecase {

    private final TipoUsuarioGateway gateway;
    private final UsuarioGateway usuarioGateway;

    private CriarTipoUsuarioUsecase(TipoUsuarioGateway gateway, UsuarioGateway usuarioGateway) {

        this.gateway = gateway;
        this.usuarioGateway = usuarioGateway;
    }

    public static CriarTipoUsuarioUsecase create(TipoUsuarioGateway gateway, UsuarioGateway usuarioGateway) {

        return new CriarTipoUsuarioUsecase(gateway, usuarioGateway);
    }

    public TipoUsuarioOutputInsertDto run(CriarTipoUsuarioInputDto input, UUID usuarioId) {

        if (!usuarioGateway.usuarioDono(usuarioId)) {
            throw new DonoRestauranteInvalidoException();
        }

        String nomeTipo = input.nomeTipo().trim();
        gateway.obterPorNome(nomeTipo).ifPresent(tipo -> {
            throw new TipoUsuarioJaExisteException(nomeTipo);
        });

        TipoUsuario tipoUsuario = TipoUsuario.criar(nomeTipo);
        TipoUsuario salvo = gateway.criar(tipoUsuario);

        return new TipoUsuarioOutputInsertDto(salvo.getId(), salvo.getNomeTipo(), salvo.isSistema());
    }
}
