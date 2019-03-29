package org.arunm.http

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.integration.config.EnableIntegration

@SpringBootApplication
@EnableIntegration
class HttpApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext context

	static void main(String[] args) {
		SpringApplication.run(HttpApplication, args)
	}

	@Override
	void run(String... args) throws Exception {
		context.getBean(RequestGateway).echo("hello")
	}
}
