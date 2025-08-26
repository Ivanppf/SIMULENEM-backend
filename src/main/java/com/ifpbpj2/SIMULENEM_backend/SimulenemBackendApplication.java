package com.ifpbpj2.SIMULENEM_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SimulenemBackendApplication {

	private static Logger log = LoggerFactory.getLogger(SimulenemBackendApplication.class);

	public static void main(String[] args) {
		var app = SpringApplication.run(SimulenemBackendApplication.class);
		var env = app.getEnvironment();
		log.info("""

				        ===========================================================\n

				        Swagger UI: http://localhost:{}/api/v1/docs.html \n

				        ===========================================================

				""", env.getProperty("server.port"));
		// SpringApplication.run(SimulenemBackendApplication.class, args);
	}

}
