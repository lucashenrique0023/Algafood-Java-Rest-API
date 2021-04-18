package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.core.openapi.model.PageableModelOpenApi;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket apiDocket() {
		
		var typeResolver = new TypeResolver();

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")).paths(PathSelectors.any())
//						.paths(PathSelectors.ant("/restaurantes/*"))
				.build().useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPutResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class)).apiInfo(apiInfo())
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.tags(new Tag("Cidades", "Gerencia as cidades"), 
						new Tag("Grupos", "Gerencia os grupos")
				);
	}

	private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno do servidor")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso nao possui representacao que poderia ser aceita pelo consumidor").build());
	}

	private List<ResponseMessage> globalPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno do servidor")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso nao possui representacao que poderia ser aceita pelo consumidor").build(),
				new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
						.message("Erro durante o processamento devido a requisicao incorreta")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_FOUND.value()).message("Recurso nao encontrado")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
						.message("Media nao suportada")
						.responseModel(new ModelRef("Problema"))
						.build()

		);
	}

	private List<ResponseMessage> globalPostResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno do servidor")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso nao possui representacao que poderia ser aceita pelo consumidor").build(),
				new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
						.message("Erro durante o processamento devido a requisicao incorreta")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
						.message("Media nao suportada")
						.responseModel(new ModelRef("Problema"))
						.build());
	}

	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Erro interno do servidor")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Recurso nao possui representacao que poderia ser aceita pelo consumidor").build(),
				new ResponseMessageBuilder().code(HttpStatus.NOT_FOUND.value()).message("Recurso nao encontrado")
						.responseModel(new ModelRef("Problema"))
						.build(),
				new ResponseMessageBuilder().code(HttpStatus.CONFLICT.value()).message("Recurso esta sendo utilizado")
						.responseModel(new ModelRef("Problema"))
						.build());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("AlgaFood API").description("API aberta para clientes e restaurantes")
				.version("1").contact(new Contact("Lucas Henrique", "https://github.com/lucashenrique0023",
						"lucas.henrique0023@gmail.com"))
				.build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
