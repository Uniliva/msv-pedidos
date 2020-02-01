package br.com.unicontas.pedidos.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.unicontas.pedidos"))
				.paths(PathSelectors.regex("/v1.*")).build().apiInfo(info());
	}

	private ApiInfo info() {
		return new ApiInfo("Msv - Produtos API", "Api de gerenciamento de produtos", "0.0.1", "Terms of service",
				new Contact("Uniliva Alves", "www.example.com", "myeaddress@company.com"), "License of API",
				"API license URL", Collections.emptyList());

	}
}
