package com.algaworks.algafood.api.exceptionhandler;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ProblemType {

	DADOS_INVALIDOS("/dados-invalidos", "Dados Invalidos", HttpStatus.BAD_REQUEST),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parametro Invalido", HttpStatus.BAD_REQUEST),
	PROPRIEDADE_NAO_RECONHECIDA("/propriedade-nao-reconhecida", "Propriedade Nao Reconhecida", HttpStatus.BAD_REQUEST),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem Incompreensivel", HttpStatus.BAD_REQUEST),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso nao encontrado", HttpStatus.NOT_FOUND),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso", HttpStatus.CONFLICT),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio", HttpStatus.BAD_REQUEST),
	ERRO_SISTEMA("/erro-sistema", "Erro no Sistema", HttpStatus.INTERNAL_SERVER_ERROR);
	
	private String title;
	private String uri;
	private HttpStatus status;
	
	ProblemType(String path, String titulo, HttpStatus status) {
		this.uri = "https://algafood.com.br" + path;
		this.title = titulo;
		this.status = status;
	}

}
