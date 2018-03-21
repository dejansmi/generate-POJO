
package com.dejans.GeneratePOJO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GeneratePOJOApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneratePOJOApplication.class, args);
	}
}
