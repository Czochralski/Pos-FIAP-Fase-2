package com.czo.restaurantes_api.infra.security;

import com.czo.restaurantes_api.infra.database.jpa.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        var usuario = usuarioRepository.buscarPorLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new UsuarioAutenticado(usuario.getId(), usuario.getLogin(), usuario.getSenha(),
                usuario.getTipoUsuario().getNomeTipo());
    }
}
