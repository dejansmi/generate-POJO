
package com.dejans.GeneratePOJO;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeneratePOJOApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneratePOJOApplication.class, args).close();
		GeneratePOJOCommands gPOJOcomm = new GeneratePOJOCommands(args);
		try {
			gPOJOcomm.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
