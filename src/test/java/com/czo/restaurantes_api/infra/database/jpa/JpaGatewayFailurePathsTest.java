package com.czo.restaurantes_api.infra.database.jpa;

import com.czo.restaurantes_api.TestFixtures;
import com.czo.restaurantes_api.infra.database.jpa.repository.ItemCardapioRepository;
import com.czo.restaurantes_api.infra.database.jpa.repository.RestauranteRepository;
import com.czo.restaurantes_api.infra.database.jpa.repository.TipoUsuarioRepository;
import com.czo.restaurantes_api.infra.database.jpa.repository.UsuarioRepository;
import com.czo.restaurantes_api.infra.database.mapper.ItemCardapioMapper;
import com.czo.restaurantes_api.infra.database.mapper.RestauranteMapper;
import com.czo.restaurantes_api.infra.database.mapper.TipoUsuarioMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaGatewayFailurePathsTest {

    @Mock RestauranteRepository restauranteRepository;
    @Mock RestauranteMapper restauranteMapper;
    @Mock ItemCardapioRepository itemRepository;
    @Mock ItemCardapioMapper itemMapper;
    @Mock TipoUsuarioRepository tipoRepository;
    @Mock TipoUsuarioMapper tipoMapper;
    @Mock UsuarioRepository usuarioRepository;

    @Test
    void gatewaysRejeitamAtualizacaoDeEntidadesQueNaoExistem() {
        when(restauranteRepository.existsById(TestFixtures.RESTAURANT_ID)).thenReturn(false);
        when(itemRepository.existsById(TestFixtures.ITEM_ID)).thenReturn(false);
        when(tipoRepository.existsById(TestFixtures.TYPE_ID)).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> new RestauranteJpaGateway(restauranteRepository, restauranteMapper)
                        .atualizar(TestFixtures.restaurante()));
        assertThrows(IllegalArgumentException.class,
                () -> new ItemCardapioJpaGateway(itemRepository, itemMapper)
                        .atualizar(TestFixtures.item()));
        assertThrows(IllegalArgumentException.class,
                () -> new TipoUsuarioJpaGateway(tipoRepository, tipoMapper)
                        .atualizar(TestFixtures.tipo("GERENTE", false)));
    }

    @Test
    void gatewayDeUsuarioRejeitaIdNuloSemConsultarBanco() {
        UsuarioJpaGateway gateway = new UsuarioJpaGateway(usuarioRepository);

        assertFalse(gateway.usuarioDono(null));

        verifyNoInteractions(usuarioRepository);
    }
}
