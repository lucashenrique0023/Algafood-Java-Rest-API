package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJPARepository<Restaurante, Long>, RestauranteRepositoryQueries, 
									JpaSpecificationExecutor<Restaurante> {
	
	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();
	
	List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	List<Restaurante> findByTaxaFreteBetweenAndNomeContaining(BigDecimal taxaInicial, BigDecimal taxaFinal, String nome);
	
	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome,@Param("id") Long cozinhaId);
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :cozinhaId and taxaFrete between :taxaInicial and :taxaFinal")
	List<Restaurante> consultarPorNomeTaxaCozinha(String nome, Long cozinhaId, BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	int countByCozinhaId(Long cozinha);
	
}
