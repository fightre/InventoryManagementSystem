package com.example.ems.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.example.ems.controller"))
				.paths(regex("/inventory.*")).build().apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfo("REST API", "Spring Boot REST API for Inventory management", "1.0",
				"Terms of service", new Contact("veera", "https://www.tekion.com/", "vsivasubramain@tekion.com"),
				"Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0");
	}
}