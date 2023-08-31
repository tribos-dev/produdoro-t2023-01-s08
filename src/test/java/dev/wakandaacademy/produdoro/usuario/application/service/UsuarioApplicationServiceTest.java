package dev.wakandaacademy.produdoro.usuario.application.service;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.StatusUsuario;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioApplicationServiceTest {

    @InjectMocks
    private UsuarioApplicationService usuarioApplicationService;
    @Mock
    private UsuarioRepository usuarioRepository;
    private UUID idUsuario = DataHelper.createUsuario().getIdUsuario();
    private String email = DataHelper.createUsuario().getEmail();
    @Test
    void alteraStatusParaFocoSucesso(){

        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(DataHelper.createUsuario());

        usuarioApplicationService.alteraStatusParaFoco(email, idUsuario);

        verify(usuarioRepository,times(1)).salva(any(Usuario.class));
    }
    @Test
    void alteraStatusParaFocoFalha(){

        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(DataHelper.createUsuario());

       assertThrows(APIException.class,()-> usuarioApplicationService.alteraStatusParaFoco(email, UUID.randomUUID()));

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