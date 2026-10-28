package com.czo.restaurantes_api.infra.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SecurityComponentsTest {

    @Test
    void encoderAceitaSenhaLegadaEHashBcrypt() {
        LegacyPasswordEncoder encoder = new LegacyPasswordEncoder();
        assertEquals("$2a$", encoder.encode("senha").substring(0, 4));
        assertTrue(encoder.matches("123456", "123456"));
        assertFalse(encoder.matches("outra", "123456"));
        assertFalse(encoder.matches("senha", null));
        assertFalse(encoder.matches(null, "senha"));

        String hash2a = new BCryptPasswordEncoder().encode("senha");
        String hash2b = "$2b$" + hash2a.substring(4);
        String hash2y = "$2y$" + hash2a.substring(4);
        assertTrue(encoder.matches("senha", hash2a));
        assertTrue(encoder.matches("senha", hash2b));
        assertTrue(encoder.matches("senha", hash2y));
        assertFalse(encoder.matches("incorreta", hash2a));
    }

    @Test
    void usuarioAutenticadoExpoeIdentidadeEAutoridade() {
        UUID id = UUID.randomUUID();
        UsuarioAutenticado usuario = new UsuarioAutenticado(id, "bruno", "segredo", "dono");

        assertEquals(id, usuario.getId());
        assertEquals("bruno", usuario.getUsername());
        assertEquals("segredo", usuario.getPassword());
        assertEquals("DONO", usuario.getTipoUsuario());
        assertEquals("ROLE_DONO", usuario.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElseThrow());
        assertTrue(usuario.isAccountNonExpired());
        assertTrue(usuario.isAccountNonLocked());
        assertTrue(usuario.isCredentialsNonExpired());
        assertTrue(usuario.isEnabled());
    }
}
