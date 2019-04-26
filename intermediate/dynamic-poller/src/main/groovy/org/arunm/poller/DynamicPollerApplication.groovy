package org.arunm.poller

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.integration.annotation.Poller
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.core.MessageSource
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.handler.LoggingHandler
import org.springframework.integration.util.DynamicPeriodicTrigger
import org.springframework.messaging.support.GenericMessage

import java.text.SimpleDateFormat
import java.time.Duration

/**
 * There is an inbound channel adapter which will geneeate a payload with a date using spring expression
 * and then forward it onto a logging channel. The logging channel will log the message.
 *
 * There will be a poller configured which will run with a default value of 5 seconds. But that will be configurable
 * via a user input and then it will change.
 */
@SpringBootApplication
@EnableIntegration
class DynamicPollerApplication implements CommandLineRunner {

    private static final Log LOGGER = LogFactory.getLog(DynamicPollerApplication.class)


    @Autowired
    DynamicPeriodicTrigger dynamicPeriodicTrigger

    static void main(String[] args) {
        SpringApplication.run(DynamicPollerApplication, args)
    }

    @Bean
    IntegrationFlow dynamicPollingFlow() {
        IntegrationFlows.from("inputChannel")
                .log(LoggingHandler.Level.INFO)
                .get()
    }


    @Bean
    @InboundChannelAdapter(value = "inputChannel", poller = @Poller(maxMessagesPerPoll = "1", trigger = "dynamicPeriodicTrigger"))
    MessageSource<String> customPollingInboundAdapter() {
        { -> new GenericMessage<String>(new SimpleDateFormat('yyyy-MM-dd HH:mm:ss.SSS').format(new Date())) }
    }

    @Bean
    DynamicPeriodicTrigger dynamicPeriodicTrigger() {
        new DynamicPeriodicTrigger(5000)
    }

    @Override
    void run(String... args) throws Exception {

        LOGGER.info '''==========================================================
                                                                          
                 Welcome to the Spring Integration Dynamic Poller Sample! 
                                                                          
                    For more information please visit:                    
                    http://www.springsource.org/spring-integration        
                                                                          
                =========================================================='''

        LOGGER.info '''=========================================================
                                                                         
                    Please press 'q + Enter' to quit the application.    
                                                                         
                ========================================================='''

       println "Please enter a non-negative numeric value and press <enter>: "
        final Scanner scanner = new Scanner(System.in)
        while (true) {
            final String input = scanner.nextLine()

            if ("q" == input.trim()) {
                break
            }

            try {

                int triggerPeriod = Integer.valueOf(input)
                System.out.println(String.format("Setting trigger period to '%s' ms", triggerPeriod))
                dynamicPeriodicTrigger.setDuration(Duration.ofMillis(5000))

            }
            catch (Exception e) {
                LOGGER.error("An exception was caught: " + e)
            }
            System.out.print("Please enter a non-negative numeric value and press <enter>: ")

        }
        LOGGER.info("Exiting application...bye.")
        scanner.close()

    }
}
