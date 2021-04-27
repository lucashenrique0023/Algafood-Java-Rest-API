package com.algaworks.algafood.api.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	CadastroCidadeService cadastroCidade;
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	CidadeInputDisassembler cidadeInputDisassembler;
	
	@GetMapping
	public List<CidadeModel> listar() {
		return cidadeModelAssembler.toModelList(cidadeRepository.findAll());
	}
	

	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId) {	
		CidadeModel cidadeModel = cidadeModelAssembler.toModel(cadastroCidade.buscarOuFalhar(cidadeId));
		
		cidadeModel.add(linkTo(CidadeController.class)
				.slash(cidadeModel.getId()).withSelfRel());
		
//		cidadeModel.add(Link.of("http://localhost:8080/cidades/1"));
		
		cidadeModel.add(linkTo(CidadeController.class)
				.withRel("cidades"));
		
//		cidadeModel.add(Link.of("http://localhost:8080/cidades/1", "cidades"));
		
		cidadeModel.getEstado().add(linkTo(EstadoController.class)
				.slash(cidadeModel.getEstado().getId())
				.withSelfRel());
		
//		cidadeModel.getEstado().add(Link.of("http://localhost:8080/estados/1"));
		
		return cidadeModel;
	}
	

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel salvar(@RequestBody @Valid CidadeInput cidadeInput) {	
		try {
			
			CidadeModel cidadeModel = cidadeModelAssembler.toModel(cadastroCidade.save(
					cidadeInputDisassembler.toDomainObject(cidadeInput)));
			
			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());
			
			return cidadeModel;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	

	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@PathVariable Long cidadeId, 
			@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			return cidadeModelAssembler.toModel(cadastroCidade.save(cidadeAtual));
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.remover(cidadeId);	
	}
}
