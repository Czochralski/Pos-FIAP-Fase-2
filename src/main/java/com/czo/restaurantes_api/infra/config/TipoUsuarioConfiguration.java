package com.czo.restaurantes_api.infra.config;

import com.czo.restaurantes_api.core.controller.TipoUsuarioController;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;
import com.czo.restaurantes_api.core.gateway.UsuarioGateway;
import com.czo.restaurantes_api.core.usecase.tipoUsuario.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TipoUsuarioConfiguration {

    @Bean
    public CriarTipoUsuarioUsecase criarTipoUsuarioUsecase(TipoUsuarioGateway gateway, UsuarioGateway usuarioGateway) {

        return CriarTipoUsuarioUsecase.create(gateway, usuarioGateway);
    }

    @Bean
    public ListarTiposUsuarioUsecase listarTiposUsuarioUsecase(TipoUsuarioGateway gateway) {

        return ListarTiposUsuarioUsecase.create(gateway);
    }

    @Bean
    public BuscarTipoUsuarioUsecase buscarTipoUsuarioUsecase(TipoUsuarioGateway gateway) {

        return BuscarTipoUsuarioUsecase.create(gateway);
    }

    @Bean
    public AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase(TipoUsuarioGateway gateway,
                                                                   UsuarioGateway usuarioGateway) {

        return AtualizarTipoUsuarioUsecase.create(gateway, usuarioGateway);
    }

    @Bean
    public ExcluirTipoUsuarioUsecase excluirTipoUsuarioUsecase(TipoUsuarioGateway gateway,
                                                               UsuarioGateway usuarioGateway) {

        return ExcluirTipoUsuarioUsecase.create(gateway, usuarioGateway);
    }

    @Bean
    public TipoUsuarioController tipoUsuarioController(
            CriarTipoUsuarioUsecase criar,
            ListarTiposUsuarioUsecase listar,
            BuscarTipoUsuarioUsecase buscar,
            AtualizarTipoUsuarioUsecase atualizar,
            ExcluirTipoUsuarioUsecase excluir) {

        return TipoUsuarioController.create(criar, listar, buscar, atualizar, excluir);
    }
}
