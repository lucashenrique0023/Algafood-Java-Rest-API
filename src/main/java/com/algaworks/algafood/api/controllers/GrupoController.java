package com.algaworks.algafood.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	GrupoRepository grupoRepository;
	
	@Autowired
	CadastroGrupoService cadastroGrupo;
	
	@Autowired
	GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	GrupoInputDisassembler grupoInputDisassembler;
	
	
	@GetMapping
	public List<GrupoModel> listar(){
		
		return grupoModelAssembler.toModelList(grupoRepository.findAll());
	}
	
	
	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {
		
		return grupoModelAssembler.toModel(cadastroGrupo.buscarOuFalhar(grupoId));
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel salvar(@RequestBody @Valid GrupoInput grupoInput) {
		
		return grupoModelAssembler.toModel(cadastroGrupo.salvar(grupoInputDisassembler.toDomainObject(grupoInput)));	
	}
	
	
	@PutMapping("/{grupoId}")
	public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
		
		Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);	
		
		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
		
		return grupoModelAssembler.toModel(cadastroGrupo.salvar(grupoAtual));	
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		cadastroGrupo.remover(grupoId);		
	}

}
