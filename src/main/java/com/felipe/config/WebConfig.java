package com.felipe.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${cors.originPatterns:default}")
	private String corsOriginPatters = "";
	
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		var allowedOrigins = corsOriginPatters.split(",");
		registry.addMapping("/**")
//			.allowedMethods("GET", "POST", "PUT", "PATCH")
			.allowedMethods("*")
			.allowedOrigins(allowedOrigins)
		.allowCredentials(true);
	}



	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//		// Query Param. http://localhost:8080/api/v1/user?mediaType=xml
//		configurer.favorParameter(true)
//			.parameterName("mediaType").ignoreAcceptHeader(true)
//			.useRegisteredExtensionsOnly(false)
//			.defaultContentType(MediaType.APPLICATION_JSON)
//				.mediaType("json", MediaType.APPLICATION_JSON)
//				.mediaType("xml", MediaType.APPLICATION_XML);
//		
		// Header Param. http://localhost:8080/api/v1/user?mediaType=xml
		configurer.favorParameter(false)
			.parameterName("mediaType").ignoreAcceptHeader(false)
			.useRegisteredExtensionsOnly(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("json", MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML);
	}
	
	
}
