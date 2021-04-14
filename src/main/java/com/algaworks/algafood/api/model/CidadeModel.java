package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(name = "Cidade", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeModel {

//	@ApiModelProperty(value = "ID da cidade", example = "1")
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Recife")
	private String nome;
	
	private EstadoModel estado;
}
