package com.algaworks.algafood.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaDAO {

	@PersistenceContext
	EntityManager manager;
	
	public List<Cozinha> listar() {
		TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);
		
		return query.getResultList();
	}
	
	public Cozinha listarPorId(Long id) {
		return manager.find(Cozinha.class, id);
	}
	
	@Transactional
	public void remover(Cozinha cozinha) {
		cozinha = this.listarPorId(cozinha.getId());
		manager.remove(cozinha);
	}
	
	@Transactional
	public Cozinha adicionar(Cozinha cozinha) {
		return manager.merge(cozinha);

	}
}
