package net.codejava.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StoreServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(StoreServiceApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(StoreServiceApplication.class);
	}

	@Bean
	public RestTemplate restTempJavaMailSenderImpllate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
