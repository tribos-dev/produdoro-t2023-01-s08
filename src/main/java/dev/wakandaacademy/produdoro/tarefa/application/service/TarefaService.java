package dev.wakandaacademy.produdoro.tarefa.application.service;

import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaIdResponse;
import dev.wakandaacademy.produdoro.tarefa.application.api.TarefaRequest;
import dev.wakandaacademy.produdoro.tarefa.domain.Tarefa;

import java.util.List;
import java.util.UUID;

public interface TarefaService {
    TarefaIdResponse criaNovaTarefa(TarefaRequest tarefaRequest);
    Tarefa detalhaTarefa(String usuario, UUID idTarefa);
	void deletaTarefa(String usuario, UUID idTarefa);
	void ativaTarefa(UUID idTarefa, UUID idUsuario, String usuarioEmail);
	void incrementaPomodoro(UUID idTarefa, String usuarioEmail);
    List<Tarefa> buscaTarefaPorUsuario(String emailUsuario, UUID idUsuario);
}
