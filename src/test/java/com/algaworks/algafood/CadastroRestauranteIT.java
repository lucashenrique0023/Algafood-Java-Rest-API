package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-tests.properties")
public class CadastroRestauranteIT {

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private List<Restaurante> restaurantes = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	public void prepararDados() {
		Cozinha c1 = new Cozinha();
		c1.setNome("Japonesa");
		c1 = cozinhaRepository.save(c1);
		
		Restaurante r1 = new Restaurante();
		r1.setNome("r1");
		r1.setTaxaFrete(BigDecimal.ONE);
		r1.setCozinha(c1);	
		
		restauranteRepository.save(r1);
		restaurantes.add(r1);
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {		
		RestAssured
		.given()
			.contentType(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());	
	}
	
	@Test
	public void deveRetornarCorpoEStatus200_QuandoConsultarRestaurantePorId() {
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.pathParam("restauranteId", restaurantes.size())
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(restaurantes.get(restaurantes.size()-1).getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoRestauranteNaoEncontrado() {
		RestAssured
		.given()
			.contentType(ContentType.JSON)
			.pathParam("restauranteId",restaurantes.size()+1)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
}
