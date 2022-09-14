package com.example.demo.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableOpenApi
public class SwaggerConfiguration {
	

	@Bean
	Docket docket()
	
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
				.build()
				.apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		// TODO Auto-generated method stub
		return new ApiInfo("Iot Tracker Mickoservice", "It will track the iot devices of the end user customer", 
				"1.0", "https://github.com/harshgarg0906/", 
				new Contact("harsh garg", "https://github.com/harshgarg0906/", "harshgarg0906@gmail.com"),
				"Terms of Use Service",
				"https://github.com/harshgarg0906/", 
				Collections.emptyList());
	}

}
