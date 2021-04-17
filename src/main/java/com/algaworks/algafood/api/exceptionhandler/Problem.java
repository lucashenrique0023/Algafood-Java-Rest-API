package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {
	
	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 5)
	private String type;
	
	@ApiModelProperty(example = "Dados Invalidos", position = 10)
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estao invalidos. Faca o preenchimento correto e tente novamente.", position = 15)
	private String detail;
	
	@ApiModelProperty(example = "Um ou mais campos estao invalidos. Faca o preenchimento correto e tente novamente.", position = 20)
	private String userMessage;
	
	@ApiModelProperty(example = "2021-04-17T13:02:54.9345614Z", position = 25)
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(example = "Lista de campos que geraram o erro", position = 30)
	private List<Field> fields;
	
	@ApiModel("CampoProblema")
	@Getter
	@Builder
	public static class Field {
		
		@ApiModelProperty(example = "nome")
		private String name;
		
		@ApiModelProperty(example = "Nome da cidade é obrigatório(a)")
		private String userMessage;
	}

}
