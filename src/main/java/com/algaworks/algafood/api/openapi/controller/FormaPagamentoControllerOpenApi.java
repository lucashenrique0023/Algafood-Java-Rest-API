package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation("Lista formas de pagamento")
	ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request);
	
	@ApiOperation("Busca uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da forma de pagamento invalido", response = Problem.class),
		@ApiResponse(code = 404, message = "Forma de pagamento nao encontrada", response = Problem.class)
	})
	ResponseEntity<FormaPagamentoModel> buscar(Long formaPagamentoId);
	
	@ApiOperation("Cadastra uma forma de pagamento")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Forma de Pagamento cadastrada")
	})
	FormaPagamentoModel salvar(FormaPagamentoInput formaPagamentoInput);
	
	@ApiOperation("Atualiza uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Forma de Pagamento atualizada", response = Problem.class),
		@ApiResponse(code = 404, message = "Forma de Pagamento nao encontrada", response = Problem.class)
	})
	FormaPagamentoModel atualizar(Long formaPagamentoId, FormaPagamentoInput formaPagamentoInput);
	
	@ApiOperation("Exclui uma Forma de Pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de Pagamento excluida"),
		@ApiResponse(code = 404, message = "Forma de Pagamento nao encontrada", response = Problem.class)
	})
	void remover(Long formaPagamentoId);
	
}
