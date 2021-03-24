package com.algaworks.algafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Grupo;

@Repository
public interface GrupoRepository extends CustomJPARepository<Grupo, Long>{

}
