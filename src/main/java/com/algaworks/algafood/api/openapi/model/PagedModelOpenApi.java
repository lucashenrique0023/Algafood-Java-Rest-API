package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedModelOpenApi<T> {
	
	private List<T> content;

	@ApiModelProperty(example = "10", value = "Quantidade de registros por pagina")
	private Long size;
	
	@ApiModelProperty(example = "50", value = "Total de registros")
	private Long totalElements;
	
	@ApiModelProperty(example = "5", value = "Total de paginas")
	private Long totalPages;
	
	@ApiModelProperty(example = "0", value = "Numero da pagina (comeca co 0)")
	private Long number;

}
