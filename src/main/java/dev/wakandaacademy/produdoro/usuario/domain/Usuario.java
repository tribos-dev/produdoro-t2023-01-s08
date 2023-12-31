package dev.wakandaacademy.produdoro.usuario.domain;

import java.util.UUID;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;

import dev.wakandaacademy.produdoro.handler.APIException;
import dev.wakandaacademy.produdoro.pomodoro.domain.ConfiguracaoPadrao;
import dev.wakandaacademy.produdoro.usuario.application.api.UsuarioNovoRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Document(collection = "Usuario")

public class Usuario {
	@Id
	private UUID idUsuario;
	@Email
	@Indexed(unique = true)
	private String email;
	private ConfiguracaoUsuario configuracao;
	@Builder.Default
	private StatusUsuario status = StatusUsuario.FOCO;
	@Builder.Default
	private Integer quantidadePomodorosPausaCurta = 0;
	
	public Usuario(UsuarioNovoRequest usuarioNovo, ConfiguracaoPadrao configuracaoPadrao) {
		this.idUsuario = UUID.randomUUID();
		this.email = usuarioNovo.getEmail();
		this.status = StatusUsuario.FOCO;
		this.configuracao = new ConfiguracaoUsuario(configuracaoPadrao);
	}
	public void alteraStatusParaFoco(){
		log.info("Altera status para foco");
		this.status = StatusUsuario.FOCO;
	}

    public void validaUsuarioPorId(UUID idUsuario) {
		if (!this.idUsuario.equals(idUsuario)){
		log.error("Id não pertence ao usuário");
			throw APIException.build(HttpStatus.UNAUTHORIZED, "Id não pertence ao usuário");
		}
    }
	public void validaUsuarioAlteraFoco(UUID idUsuario){
		if(!this.getIdUsuario().equals(idUsuario)){
			throw APIException.build(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
		}
	}

    public void mudaStatusPausaLonga() {
		this.status = StatusUsuario.PAUSA_LONGA;
    }
	
	public void validaUsuario(UUID idUsuario) {
		log.warn("[inicia] Usuario - validaUsuario");
		if(!this.idUsuario.equals(idUsuario)) {
			log.error("Credencial de autenticação {} não é valida", idUsuario);
			throw APIException.build(HttpStatus.UNAUTHORIZED, "Credencial de autenticação não é valida");
		}
		log.warn("[finaliza] Usuario - validaUsuario");
	}

	public void mudaStatusPausaCurta() {
		log.info("[inicia] Usuario - mudaStatusPausaCurta");
		this.status = StatusUsuario.PAUSA_CURTA;
		log.info("[finaliza] Usuario - mudaStatusPausaCurta");
	}
}