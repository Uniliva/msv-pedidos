package br.com.unicontas.pedidos.config;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalTime.class, String.class)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.unicontas.pedidos"))
				.paths(PathSelectors.regex("/v1.*"))
				.build()
		        .useDefaultResponseMessages(false)
		        .globalResponseMessage(RequestMethod.GET, Arrays.asList(resBadRequest, resInternalError))
		        .globalResponseMessage(RequestMethod.DELETE, Arrays.asList(resNoContent, resBadRequest, resInternalError))
		        .globalResponseMessage(RequestMethod.POST, Arrays.asList(resCreated, resBadRequest, resInternalError))
		        .globalResponseMessage(RequestMethod.PUT, Arrays.asList(resBadRequest, resInternalError))
		        .apiInfo(info());
	}

	private ApiInfo info() {
		return new ApiInfo("Msv - Produtos API", "Api de gerenciamento de Pedidos e Produtos", "0.0.1", "Terms of service",
				new Contact("Uniliva Alves", "www.example.com", "myeaddress@company.com"), "License of API",
				"API license URL", Collections.emptyList());

	}
	
	
	final ResponseMessage resBadRequest = new ResponseMessageBuilder()
            .code(400)
            .message("Requisiçcao invalida!")
            .build();
	
	final ResponseMessage resInternalError= new ResponseMessageBuilder()
            .code(500)
            .message("Erro interno!")
            .responseModel(new ModelRef("Error"))
            .build();
	
	final ResponseMessage resCreated= new ResponseMessageBuilder()
            .code(201)
            .message("Recurso criado!")
            .build();
	
	final ResponseMessage resNoContent= new ResponseMessageBuilder()
            .code(204)
            .message("No content!")
            .build();
		
}
