package org.arunm.http

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.dsl.MessageChannels
import org.springframework.integration.http.dsl.Http
import org.springframework.integration.http.inbound.HttpRequestHandlingMessagingGateway
import org.springframework.integration.http.inbound.RequestMapping
import org.springframework.messaging.MessageChannel

@Configuration
class IntegrationConfig {


    @Bean
    IntegrationFlow flowDefinition() {
        IntegrationFlows.from(RequestGateway)
                .handle(Http.outboundChannelAdapter("http://localhost:8080/http/receiveGateway")
        .httpMethod(HttpMethod.POST)
        .expectedResponseType(String))
        .get()
    }


    @Bean
    HttpRequestHandlingMessagingGateway httpGate() {
        HttpRequestHandlingMessagingGateway gateway = new HttpRequestHandlingMessagingGateway(true)
        RequestMapping requestMapping = new RequestMapping()
        requestMapping.setMethods(HttpMethod.POST)
        requestMapping.setPathPatterns("/receiveGateway")
        gateway.setRequestMapping(requestMapping)
        gateway.setRequestChannel(requestChannel())
        return gateway
    }

    MessageChannel requestChannel() {
        MessageChannels.direct("receiveChannel").get()
    }

    @Bean
    IntegrationFlow inboundAdapter() {
        IntegrationFlows.from(requestChannel())
        .handle(String, {p -> "payload + ' from the other side'"})
        .get()
    }

}
