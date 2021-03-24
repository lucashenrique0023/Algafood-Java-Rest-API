package com.algaworks.algafood.api.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		
		return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
	}

	
	public List<FormaPagamentoModel> toModelList(Collection<FormaPagamento> list) {
		
		List<FormaPagamentoModel> modelList = new ArrayList<>();
		list.forEach(formaPagamento -> modelList.add(toModel(formaPagamento)));
		
		return modelList;
	}
	
}
