package com.algaworks.algafood.api.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PermissaoModel toModel(Permissao permissao) {
		
		return modelMapper.map(permissao, PermissaoModel.class);
	}

	
	public List<PermissaoModel> toModelList(Collection<Permissao> list) {
		
		List<PermissaoModel> modelList = new ArrayList<>();
		list.forEach(permissao -> modelList.add(toModel(permissao)));
		
		return modelList;
	}
	
}
