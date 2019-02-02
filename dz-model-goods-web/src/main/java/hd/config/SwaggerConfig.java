package hd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 接口文档
 * 
 * @author mao
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket buildDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(this.buildApiInfo()).select().apis(RequestHandlerSelectors.basePackage("hd.controller")).paths(PathSelectors.any()).build();
	}

	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder().title("互动深世界").description("互动深世界商品客户端接口测试文档").termsOfServiceUrl("").version("1.0").build();
	}

}
