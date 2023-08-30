package dev.wakandaacademy.produdoro.usuario.application.service;

import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioApplicationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioApplicationService usuarioApplicationService;

    @Test
    @DisplayName("Teste unitário pausa longa")
    void pausaLongaTest() {
        UUID idUsuario = UUID.randomUUID();
        Usuario usuario = Usuario.builder()
                .idUsuario(idUsuario)
                .email("cirilo@gmail.com")
                .build();

        when(usuarioRepository.buscaUsuarioPorId(idUsuario)).thenReturn(usuario);
        usuarioApplicationService.pausaLonga("cirilo@gmail.com", idUsuario);
        verify(usuarioRepository).buscaUsuarioPorId(idUsuario);
        verify(usuarioRepository).salva(usuario);
    }
}