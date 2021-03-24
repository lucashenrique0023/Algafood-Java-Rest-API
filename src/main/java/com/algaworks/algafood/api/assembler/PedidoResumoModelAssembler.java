package com.algaworks.algafood.api.assembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoModel toModel(Pedido pedido) {
		
		return modelMapper.map(pedido, PedidoResumoModel.class);
	}

	
	public List<PedidoResumoModel> toModelList(List<Pedido> list) {
		
		List<PedidoResumoModel> modelList = new ArrayList<>();
		list.forEach(pedido -> modelList.add(toModel(pedido)));
		
		return modelList;
	}
}
