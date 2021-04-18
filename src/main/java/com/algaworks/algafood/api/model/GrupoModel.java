package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoModel {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Gerencia")
	private String nome;
	
}
