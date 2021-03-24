package com.algaworks.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonRootName;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;

@Data
@JsonRootName("cozinhas")
public class CozinhaXmlWrapper {
	
//	@NonNull
//	@JsonProperty("cozinha")
//	@JacksonXmlElementWrapper(useWrapping = false)
//	private List<Cozinha> lista;

}
