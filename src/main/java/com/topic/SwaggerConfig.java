package com.topic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/*
	 * @Bean public Docket api() { return new
	 * Docket(DocumentationType.SWAGGER_2).select()
	 * .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
	 * .paths(PathSelectors.any()).build() .apiInfo(apiInfo()); }
	 */

	@Bean
	public Docket postsApi() {


		/*
		 * List<Parameter> aParameters = new ArrayList<>(); aParameters.add(new
		 * ParameterBuilder().name("Authorization").description("authorization-id")
		 * .modelRef(new
		 * ModelRef("string")).parameterType("header").required(true).build());
		 */

		return new Docket(DocumentationType.SWAGGER_2).groupName("xebia test").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().pathMapping("/").enable(true);
//				.globalOperationParameters(aParameters);

	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("xebia test API", "Some custom description of API.", "API TOS", "Terms of service","xebia test", "License of API", "API license URL");
		return apiInfo;
	}

}
