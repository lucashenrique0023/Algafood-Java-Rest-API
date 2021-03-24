package com.algaworks.algafood.api.assembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoModel toModel(Pedido pedido) {
		
		return modelMapper.map(pedido, PedidoModel.class);
	}

	
	public List<PedidoModel> toModelList(List<Pedido> list) {
		
		List<PedidoModel> modelList = new ArrayList<>();
		list.forEach(pedido -> modelList.add(toModel(pedido)));
		
		return modelList;
	}
}
