package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

	public FotoProdutoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public FotoProdutoNaoEncontradaException(Long produtoId, Long restauranteId) {
		this(String.format("Nao existe um cadastro de foto do produto com codigo %d para o restaurante %d.", produtoId, restauranteId));
	}
	

}
