package com.algaworks.algafood.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/grupos/{grupoId}/permissoes",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissoesController implements GrupoPermissaoControllerOpenApi {
	
	@Autowired
	CadastroGrupoService cadastroGrupo;
	
	@Autowired
	PermissaoModelAssembler permissaoModelAssembler;	
	
	@GetMapping
	public List<PermissaoModel> listar(@PathVariable Long grupoId){
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		
		return permissaoModelAssembler.toModelList(grupo.getPermissoes());
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarGrupo(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		
		cadastroGrupo.desassociarPermissao(grupoId, permissaoId);
		
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarGrupo(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		
		cadastroGrupo.associarPermissao(grupoId, permissaoId);
		
	}

}
