package com.paparazzi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * 添加摘要信息(Docket)
	 */
	@Bean
	public Docket controllerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder()
						.title("狗仔学社接口平台")
						.description("狗仔学社APP开放平台")
						.version("版本号:1.0")
						.build())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.paparazzi.controller"))
				.paths(PathSelectors.any())
				.build();
	}
}
