package com.country.details.democountry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

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

}
