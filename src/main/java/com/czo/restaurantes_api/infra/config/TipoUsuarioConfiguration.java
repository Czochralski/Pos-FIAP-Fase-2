package com.czo.restaurantes_api.infra.config;

import com.czo.restaurantes_api.core.controller.TipoUsuarioController;
import com.czo.restaurantes_api.core.gateway.TipoUsuarioGateway;
import com.czo.restaurantes_api.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TipoUsuarioConfiguration {

    @Bean
    public CriarTipoUsuarioUsecase criarTipoUsuarioUsecase(TipoUsuarioGateway gateway) {
        return CriarTipoUsuarioUsecase.create(gateway);
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
    public AtualizarTipoUsuarioUsecase atualizarTipoUsuarioUsecase(TipoUsuarioGateway gateway) {
        return AtualizarTipoUsuarioUsecase.create(gateway);
    }

    @Bean
    public ExcluirTipoUsuarioUsecase excluirTipoUsuarioUsecase(TipoUsuarioGateway gateway) {
        return ExcluirTipoUsuarioUsecase.create(gateway);
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
