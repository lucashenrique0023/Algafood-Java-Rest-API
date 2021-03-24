package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-tests.properties")
class CadastroCozinhaIT {

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	CozinhaRepository cozinhaRepository;
	
	private List<Cozinha> cozinhas = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		
		
		RestAssured.given()		
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarTotalCozinhasCadastradas_QuandoConsultarCozinhas() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(cozinhas.size()));
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarNovaCozinha() {
		
		RestAssured.given()
			.body(ResourceUtils.getContentFromResource("/json/cozinhaCadastro.json"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		RestAssured
		.given()
			.pathParam("cozinhaId", cozinhas.size()+1)
			.contentType(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarCorpoEStatusCorretos_QuandoConsultarCozinhaExistente() {
		RestAssured
		.given()
			.pathParam("cozinhaId", cozinhas.size())
			.contentType(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(cozinhas.get(cozinhas.size()-1).getNome()));
	}
	
	private void prepararDados() {
		 Cozinha cozinha = new Cozinha();
		 cozinha.setNome("Tailandesa");
		 cozinhaRepository.save(cozinha);
		 cozinhas.add(cozinha);
		 
		 Cozinha cozinha2 = new Cozinha();
		 cozinha2.setNome("Japonesa");
		 cozinhaRepository.save(cozinha2);
		 cozinhas.add(cozinha2);
		 
		 
	}

}
