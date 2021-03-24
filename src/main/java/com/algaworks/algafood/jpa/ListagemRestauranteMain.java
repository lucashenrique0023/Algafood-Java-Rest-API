package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class ListagemRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);	
		
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);		
		
		for (Restaurante r : restauranteRepository.findAll()) {
			System.out.printf("%s - %f : %s \n", r.getNome(), r.getTaxaFrete(), r.getCozinha().getNome());
		}
	}
}
