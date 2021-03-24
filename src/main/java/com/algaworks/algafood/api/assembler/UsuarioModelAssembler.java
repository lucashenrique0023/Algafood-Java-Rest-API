package com.algaworks.algafood.api.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public UsuarioModel toModel(Usuario usuario) {
		
		return modelMapper.map(usuario, UsuarioModel.class);
	}

	
	public List<UsuarioModel> toModelList(Collection<Usuario> list) {
		
		List<UsuarioModel> modelList = new ArrayList<>();
		list.forEach(usuario -> modelList.add(toModel(usuario)));
		
		return modelList;
	}
}