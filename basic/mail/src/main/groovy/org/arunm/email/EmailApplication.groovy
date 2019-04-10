package org.arunm.email

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
import org.springframework.integration.mail.dsl.Mail
import org.springframework.mail.javamail.JavaMailSender

@SpringBootApplication
@EnableIntegration
class EmailApplication implements CommandLineRunner{

	static void main(String[] args) {
		SpringApplication.run(EmailApplication, args)
	}

	@Autowired
	private ApplicationContext context;

	@Autowired
	private JavaMailSender javaMailSender;

	@Bean
	DirectChannel inputChannel() {
		return new DirectChannel()
	}

	@Autowired
	EmailService emailService

	@Bean
	IntegrationFlow helloWorld() {
		IntegrationFlows.from("inputChannel")
				.transform(emailService)
				.handle(Mail.outboundAdapter(javaMailSender))
		.get()

	}

	@Bean
	QueueChannel outputChannel() {
		return new QueueChannel(10)
	}

	@Override
	void run(String... args) throws Exception {
//		MessageChannel inputChannel = context.getBean("inputChannel", MessageChannel.class);
//		PollableChannel outputChannel = context.getBean("outputChannel", PollableChannel.class);
//		inputChannel.send(new GenericMessage<String>("World"));
//		println outputChannel.receive().getPayload()
//		inputChannel.send(new GenericMessage<String>("Arun"));
//		println outputChannel.receive().getPayload()
	}
}
