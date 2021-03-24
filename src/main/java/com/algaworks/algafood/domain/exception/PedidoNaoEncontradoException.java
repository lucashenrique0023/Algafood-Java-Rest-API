package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

	public PedidoNaoEncontradoException(String codigoPedido) {
		super(String.format("Nao existe um cadastro de pedido com codigo %s", codigoPedido));
	}
}
