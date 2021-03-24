package com.algaworks.algafood.api.assembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public EstadoModel toModel(Estado estado) {
		
		return modelMapper.map(estado, EstadoModel.class);
	}

	
	public List<EstadoModel> toModelList(List<Estado> list) {
		
		List<EstadoModel> modelList = new ArrayList<>();
		list.forEach(estado -> modelList.add(toModel(estado)));
		
		return modelList;
	}
}
