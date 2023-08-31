package dev.wakandaacademy.produdoro.tarefa.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.wakandaacademy.produdoro.DataHelper;
import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaIdResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.application.repository.TarefaRepository;
import dev.wakandaacademy.produdoro.tarefa.domain.StatusAtivacaoTarefa;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;
import dev.wakandaacademy.produdoro.usuario.application.repository.UsuarioRepository;
import dev.wakandaacademy.produdoro.usuario.domain.Usuario;

@ExtendWith(MockitoExtension.class)
class TarefaApplicationServiceTest {

    //	@Autowired
    @InjectMocks
    TarefaApplicationService tarefaApplicationService;

    //	@MockBean
    @Mock
    TarefaRepository tarefaRepository;

    //	@MockBean
    @Mock
    UsuarioRepository usuarioRepository;

    @Test
    void deveRetornarIdTarefaNovaCriada() {
        TarefaRequest request = getTarefaRequest();
        when(tarefaRepository.salva(any())).thenReturn(new Tarefa(request));

        TarefaIdResponse response = tarefaApplicationService.criaNovaTarefa(request);

        assertNotNull(response);
        assertEquals(TarefaIdResponse.class, response.getClass());
        assertEquals(UUID.class, response.getIdTarefa().getClass());
    }


    public TarefaRequest getTarefaRequest() {
        TarefaRequest request = new TarefaRequest("tarefa 1", UUID.randomUUID(), null, null, 0);
        return request;
    }
    
    @Test
    @DisplayName("Teste ativa tarefa")
    void ativaTarefaDeveRetornarTarefaAtiva() {
    	UUID idTarefa = DataHelper.createTarefa().getIdTarefa();
    	UUID idUsuario = DataHelper.createUsuario().getIdUsuario();
    	String email = "123@gmail.com";
    	Tarefa retorno = DataHelper.getTarefaForAtivaTarefa();
    	when (usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(DataHelper.createUsuario()); 
    	when (tarefaRepository.buscaTarefaPorId(any())).thenReturn(Optional.of(DataHelper.createTarefa()));
    	tarefaApplicationService.ativaTarefa(idTarefa, idUsuario, email);
    	verify(tarefaRepository, times(1)).buscaTarefaPorId(idTarefa);
    	assertEquals(StatusAtivacaoTarefa.ATIVA, retorno.getStatusAtivacao());
    @DisplayName("Teste Incrementa Pomodoro")
    void incrementaPomodoro_idTarefaETokenValido_deveIncrementarUmPomodoro() {
    	//DADO
    	Tarefa tarefa = DataHelper.createTarefa();
    	UUID idTarefa = tarefa.getIdTarefa();
    	Usuario usuario = DataHelper.createUsuario();
    	String usuarioEmail = usuario.getEmail();
    	//QUANDO
    	when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario);
    	when(tarefaRepository.buscaTarefaPorId(any(UUID.class))).thenReturn(Optional.of(tarefa));
    	tarefaApplicationService.incrementaPomodoro(idTarefa, usuarioEmail);
    	//ENTÃO
    	verify(tarefaRepository, times(1)).salva(any(Tarefa.class));
    }
    
    @Test
    @DisplayName("Teste Incrementa Pomodoro - Usuario Não é dono da tarefa")
    void incrementaPomodoro_Usuario_Nao_E_Dono_Da_Tarefa() {
    	//DADO 
    	Tarefa tarefa = DataHelper.createTarefa();
    	UUID idTarefa = tarefa.getIdTarefa();
    	Usuario usuario2 = DataHelper.createUsuarioDiferente();
    	String usuarioEmail = usuario2.getEmail();
    	//QUANDO
    	when(usuarioRepository.buscaUsuarioPorEmail(anyString())).thenReturn(usuario2);
    	when(tarefaRepository.buscaTarefaPorId(any(UUID.class))).thenReturn(Optional.of(tarefa));
    	APIException ex = assertThrows(APIException.class, () -> {
    	tarefaApplicationService.incrementaPomodoro(idTarefa, usuarioEmail);
    	});
    	assertEquals("Usuário não é dono da Tarefa solicitada!", ex.getMessage());
    }
}
