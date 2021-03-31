package com.algaworks.algafood.infrastructure.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	EntityManager manager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();	
		CriteriaQuery<VendaDiaria> criteriaQuery = builder.createQuery(VendaDiaria.class);	
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		Expression<Date> functionDateOffsetTimeZone = builder.function("convert_tz", 
				Date.class, 
				root.get("dataCriacao"),
				builder.literal("+00:00"), 
				builder.literal(timeOffset));
		
		Expression<Date> functionDateDataCriacao = builder.function("date", Date.class, functionDateOffsetTimeZone);		
		
		Selection<VendaDiaria> selection = builder.construct(VendaDiaria.class, 
				functionDateDataCriacao, 
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		criteriaQuery.select(selection);
		criteriaQuery.groupBy(functionDateDataCriacao);
		
		var predicates = new ArrayList<Predicate>();
		
		predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		if (filtro.getDataCriacaoInicio() != null) {		
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
		}
		
		if (filtro.getDataCriacaoFim() != null) {		
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
		}
		
		if (filtro.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
		return manager.createQuery(criteriaQuery).getResultList();	
	}
}
