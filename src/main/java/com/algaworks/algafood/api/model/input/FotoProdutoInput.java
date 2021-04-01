package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.core.validation.FileSize;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

	@NotNull
	@FileSize(max = "50KB")
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
	
}
