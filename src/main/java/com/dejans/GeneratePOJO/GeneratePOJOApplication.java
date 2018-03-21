
package com.dejans.GeneratePOJO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeneratePOJOApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneratePOJOApplication.class, args);
		GeneratePOJOCommands gPOJOcomm = new GeneratePOJOCommands(args);
		gPOJOcomm.execute();
	}
}
