package com.algaworks.algafood.api.assembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public CidadeModel toModel(Cidade cidade) {
		
		return modelMapper.map(cidade, CidadeModel.class);
	}

	
	public List<CidadeModel> toModelList(List<Cidade> list) {
		
		List<CidadeModel> modelList = new ArrayList<>();
		list.forEach(cidade -> modelList.add(toModel(cidade)));
		
		return modelList;
	}

}
