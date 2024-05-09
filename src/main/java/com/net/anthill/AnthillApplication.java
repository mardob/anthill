package com.net.anthill;

import com.net.anthill.initialSetup.NewDbSetup;
import com.net.anthill.model.UserLogin;
import com.net.anthill.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableTransactionManagement
public class AnthillApplication {

	static NewDbSetup newDbSetup;

	@Autowired
	public AnthillApplication(NewDbSetup newDbSetup) {
		AnthillApplication.newDbSetup = newDbSetup;
	}

	public static void main(String[] args) {
		SpringApplication.run(AnthillApplication.class, args);
		newDbSetup.initiateDatabaseOnFirstDbExecution();
	}



}
