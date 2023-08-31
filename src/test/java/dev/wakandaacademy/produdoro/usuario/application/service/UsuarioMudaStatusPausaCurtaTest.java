package dev.wakandaacademy.produdoro.usuario.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;

@ExtendWith(MockitoExtension.class)
class UsuarioMudaStatusPausaCurtaTest {
	@InjectMocks
	private UsuarioApplicationService usuarioApplicationService;
	@Mock
	private UsuarioRepository usuarioRepository;
	private Usuario usuario = DataHelper.createUsuario();
	private String email = usuario.getEmail();
	private UUID idUsuario = usuario.getIdUsuario();

	@Test
	void UsuarioMudaStatusPausaCurtaSucesso() {
		
		when(usuarioRepository.buscaUsuarioPorEmail(any()))
		.thenReturn(usuario);
		usuarioApplicationService.mudaStatusPausaCurta(email, idUsuario);
		verify(usuarioRepository, times(1)).salva(any());
	}

	@Test
	void UsuarioMudaStatusPausaCurtaFalha() {
		
		when(usuarioRepository.buscaUsuarioPorEmail(any()))
		.thenReturn(usuario);
		APIException ex = assertThrows(APIException.class, ()->usuarioApplicationService.mudaStatusPausaCurta(email, UUID.randomUUID()));
		assertEquals(APIException.class, ex.getClass());
		assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusException());
		assertEquals("Credencial de autenticação não é valida", ex.getMessage());
	}

}
