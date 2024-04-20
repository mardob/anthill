package com.net.anthill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableTransactionManagement
public class AnthillApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnthillApplication.class, args);
	}

}
