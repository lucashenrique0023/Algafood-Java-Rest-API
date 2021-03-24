package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJPARepository<Cozinha, Long>{

	List<Cozinha> findCozinhaByNome(String name);
		
}
