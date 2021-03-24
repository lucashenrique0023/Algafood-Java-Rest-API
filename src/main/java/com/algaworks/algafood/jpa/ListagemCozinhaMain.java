package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;

public class ListagemCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		
		CozinhaDAO listagemCozinha = applicationContext.getBean(CozinhaDAO.class);	
		
		
		for (Cozinha c : listagemCozinha.listar()) {
			System.out.println(c.getNome());
		}
		
		Cozinha cozinha1 = listagemCozinha.listarPorId(1L);
		System.out.printf("%d : %s \n", cozinha1.getId(), cozinha1.getNome());
	}
	
	
	
}
