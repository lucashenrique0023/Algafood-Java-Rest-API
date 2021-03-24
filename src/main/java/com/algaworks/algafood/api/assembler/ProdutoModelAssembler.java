package com.algaworks.algafood.api.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoModel toModel(Produto produto) {
		
		return modelMapper.map(produto, ProdutoModel.class);
	}

	
	public List<ProdutoModel> toModelList(Collection<Produto> list) {
		
		List<ProdutoModel> modelList = new ArrayList<>();
		list.forEach(produto -> modelList.add(toModel(produto)));
		
		return modelList;
	}
	
}
