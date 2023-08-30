package dev.wakandaacademy.produdoro.tarefa.application.service;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisualizaTarefasUsuarioTest {

    @InjectMocks
    private TarefaApplicationService tarefaApplicationService;
    @Mock
    private TarefaRepository tarefaRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    private UUID idUsuario = DataHelper.createUsuario().getIdUsuario();
    private String email = DataHelper.createUsuario().getEmail();

    @Test
    void visualizaTodasTarefasSucesso(){

        when(tarefaRepository.buscaTarefasPorUsuario(any(UUID.class))).thenReturn(DataHelper.createListTarefa());
        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(DataHelper.createUsuario());
        List<Tarefa> response = tarefaApplicationService.buscaTarefaPorUsuario(email, idUsuario);
        assertNotNull(response);
        assertEquals(8, response.size());
        assertFalse(response.isEmpty());
    }
    @Test
    void visualizaTodasTarefasFalha(){

        when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(DataHelper.createUsuario());
        APIException ex = assertThrows(APIException.class, () -> tarefaApplicationService.buscaTarefaPorUsuario(email, UUID.randomUUID()));
        assertNotNull(ex);
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusException());
        assertEquals("Usuário não encontrado", ex.getMessage());
    }
}