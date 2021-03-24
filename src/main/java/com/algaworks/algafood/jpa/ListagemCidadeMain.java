package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

public class ListagemCidadeMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
			
		CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);	
		
		for (Cidade c : cidadeRepository.findAll()) {
			System.out.printf("%s - %s \n", c.getNome(), c.getEstado().getNome());
		}
	}
}
