package com.algaworks.algafood.api.assembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public RestauranteModel toModel(Restaurante restaurante) {
		
		return modelMapper.map(restaurante, RestauranteModel.class);
	}

	
	public List<RestauranteModel> toModelList(List<Restaurante> list) {
		
		List<RestauranteModel> modelList = new ArrayList<>();
		list.forEach(restaurante -> modelList.add(toModel(restaurante)));
		
		return modelList;
	}
	
}
