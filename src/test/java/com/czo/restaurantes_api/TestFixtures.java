package com.czo.restaurantes_api;

import com.czo.restaurantes_api.core.domain.Endereco;
import com.czo.restaurantes_api.core.domain.ItemCardapio;
import com.czo.restaurantes_api.core.domain.Restaurante;
import com.czo.restaurantes_api.core.domain.TipoUsuario;

import java.math.BigDecimal;
import java.util.UUID;

public final class TestFixtures {

    public static final UUID OWNER_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
    public static final UUID SECOND_OWNER_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");
    public static final UUID RESTAURANT_ID = UUID.fromString("66666666-6666-6666-6666-666666666666");
    public static final UUID ITEM_ID = UUID.fromString("77777777-7777-7777-7777-777777777777");
    public static final UUID TYPE_ID = UUID.fromString("88888888-8888-8888-8888-888888888888");

    private TestFixtures() {
    }

    public static Endereco endereco() {
        return Endereco.reconstruir(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                "Rua das Flores", "10", "Centro", "Recife", "PE", "50000-000");
    }

    public static Restaurante restaurante() {
        return Restaurante.reconstruir(RESTAURANT_ID, "Restaurante Teste", endereco(),
                "Brasileira", "10:00 às 22:00", OWNER_ID);
    }

    public static ItemCardapio item() {
        return ItemCardapio.reconstruir(ITEM_ID, "Prato Teste", "Descrição teste",
                new BigDecimal("25.50"), false, "https://exemplo.test/prato.jpg", RESTAURANT_ID);
    }

    public static TipoUsuario tipo(String nome, boolean sistema) {
        return TipoUsuario.reconstruir(TYPE_ID, nome, sistema);
    }
}
