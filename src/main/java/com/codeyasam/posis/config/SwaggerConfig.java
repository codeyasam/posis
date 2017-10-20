package com.codeyasam.posis.config;

import static springfox.documentation.builders.PathSelectors.any;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile({"dev", "prod"})
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("posis").select()
				.apis(RequestHandlerSelectors.basePackage("com.codeyasam.posis"))
				.paths(any()).build().apiInfo(new ApiInfo("Room Services", 
						"A set of services to provide data access to rooms", "1.0.0", null, 
						new Contact("Codeyasam", "https://www.codeyasam.com/", null), null, null));
	}	
}
