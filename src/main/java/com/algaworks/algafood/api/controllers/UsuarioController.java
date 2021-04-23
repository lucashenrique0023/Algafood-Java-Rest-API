package com.algaworks.algafood.api.controllers;

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

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	CadastroUsuarioService cadastroUsuario;
	
	@Autowired
	UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	UsuarioInputDisassembler usuarioInputDisassembler;

	
	@GetMapping
	public List<UsuarioModel> listar(){
		
		return usuarioModelAssembler.toModelList(usuarioRepository.findAll());
	}
	
	
	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId) {
		
		return usuarioModelAssembler.toModel(cadastroUsuario.buscarOuFalhar(usuarioId));
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
		
		return usuarioModelAssembler.toModel(cadastroUsuario.save(usuarioInputDisassembler.toDomainObject(usuarioInput)));	
	}
	
	
	@PutMapping("/{usuarioId}")
	public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
		
		Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);	
		
		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
		
		return usuarioModelAssembler.toModel(cadastroUsuario.save(usuarioAtual));	
	}
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput usuarioSenhaInput) {
	
		cadastroUsuario.atualizarSenha(usuarioId, usuarioSenhaInput.getSenhaAtual(), usuarioSenhaInput.getNovaSenha());
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long usuarioId) {
			cadastroUsuario.remover(usuarioId);		
	}
}
