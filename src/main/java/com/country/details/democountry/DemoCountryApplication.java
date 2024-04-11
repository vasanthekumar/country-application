package com.country.details.democountry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author kvasanthakumar
 */
@SpringBootApplication
@EnableCaching
public class DemoCountryApplication {

	/**
	 * Launches the application
	 * @param args - Application startup arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(DemoCountryApplication.class, args);
	}

	/**
	 * Description: Get webclient
	 * @return Webclient
	 */
	@Bean
	public WebClient webClient() {
		return WebClient.create();
	}
}
