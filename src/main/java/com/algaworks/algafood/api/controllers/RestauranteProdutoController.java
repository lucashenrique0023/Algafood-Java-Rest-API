package com.algaworks.algafood.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	ProdutoInputDisassembler produtoInputDisassembler;
	
	@GetMapping
	public List<ProdutoModel> listar(@PathVariable Long restauranteId,
			@RequestParam(required=false) boolean incluirInativos) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		List<Produto> todosProdutos = null;
		
		if (incluirInativos) {
			todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
		} else {
			todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
		}
		
		return produtoModelAssembler.toModelList(todosProdutos);
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		cadastroRestaurante.buscarOuFalhar(restauranteId);		
		return produtoModelAssembler.toModel(cadastroProdutoService.buscarOuFalhar(produtoId, restauranteId));
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoModel atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,@RequestBody @Valid ProdutoInput produtoInput) {
		
		cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(produtoId, restauranteId);
		
		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
		
		return produtoModelAssembler.toModel(cadastroProdutoService.salvar(produtoAtual));	
	}
	
	@PostMapping
	public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);	
		
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);
		
		return produtoModelAssembler.toModel(cadastroProdutoService.salvar(produto));	
	}
	
//	@DeleteMapping("/{produtoId}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void remover(@PathVariable Long restauranteId, @PathVariable Long estadoId) {
//			Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
//			
//			cadastroEstado.remover(estadoId);		
//	}
}
