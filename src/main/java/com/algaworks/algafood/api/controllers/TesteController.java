package com.algaworks.algafood.api.controllers;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping(path = "/teste")
public class TesteController {
	
	@Autowired
	CozinhaRepository cozinhaRepository;
	
	@Autowired
	RestauranteRepository restauranteRepository;
	
	@GetMapping
	public List<Cozinha> findCozinhaByNome(String name) {
		return cozinhaRepository.findCozinhaByNome(name);
		//return null;
	}
	
	@GetMapping("/querymethod")
	public List<Restaurante> findByTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal, String nome) {
		return restauranteRepository.findByTaxaFreteBetweenAndNomeContaining(taxaInicial, taxaFinal, nome);
	}
	
	@GetMapping("/orm")
	public List<Restaurante> findByTaxaFrete(String nome, Long cozinhaId, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.consultarPorNomeTaxaCozinha(nome, cozinhaId, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/customrepo")
	public List<Restaurante> findByTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
//		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
		return restauranteRepository.findCriteria(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("spec")
	public List<Restaurante> findByTaxaFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
	}
	
	@GetMapping("referenciaCircular")
	public List<Restaurante> findFreteGratisNomeSem(String nome){
		return restauranteRepository.findFreteGratis(nome);
	}
	
	@GetMapping("/restaurante/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {
		return restauranteRepository.buscarPrimeiroResultado();
	}
	
	@GetMapping("/user")
	public String user(@Valid User user) {
		return user.getName();
	}
}

@Getter
@Setter
class User {
	
	@NotBlank
	private String name;
	private Long lineNumber;
	private String platform;
	
}
