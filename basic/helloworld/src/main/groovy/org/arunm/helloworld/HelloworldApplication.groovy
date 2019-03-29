package org.arunm.helloworld

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.channel.QueueChannel
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.PollableChannel
import org.springframework.messaging.support.GenericMessage

@SpringBootApplication
@EnableIntegration
class HelloworldApplication implements CommandLineRunner{

	static void main(String[] args) {
		SpringApplication.run(HelloworldApplication, args)
	}

	@Autowired
	private ApplicationContext context;



	@Autowired
	HelloService helloService

	@Bean
	DirectChannel inputChannel() {
		return new DirectChannel()
	}

	@Bean
	IntegrationFlow helloWorld() {
		IntegrationFlows.from("inputChannel")
			.handle(helloService,"sayHello")
			.transform()
			.channel("outputChannel")
		.get()

	}

	@Bean
	QueueChannel outputChannel() {
		return new QueueChannel(10)
	}

	@Override
	void run(String... args) throws Exception {
		MessageChannel inputChannel = context.getBean("inputChannel", MessageChannel.class);
		PollableChannel outputChannel = context.getBean("outputChannel", PollableChannel.class);
		inputChannel.send(new GenericMessage<String>("World"));
		println outputChannel.receive().getPayload()
		inputChannel.send(new GenericMessage<String>("Arun"));
		println outputChannel.receive().getPayload()
	}
}
