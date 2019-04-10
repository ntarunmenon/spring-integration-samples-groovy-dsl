package org.arunm.email

import groovy.text.GStringTemplateEngine
import groovy.text.Template
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.integration.annotation.Transformer
import org.springframework.mail.SimpleMailMessage
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct


@Component
class EmailService {

    @Value("classpath:emailTemplate.tm")
    Resource emailTemplate;

    Template template

    @PostConstruct
    void setUpTemplate() {
        template = new GStringTemplateEngine().createTemplate(
                new InputStreamReader(emailTemplate.getInputStream())
        )
    }
    @Transformer
    SimpleMailMessage generateMailMessage(@Payload String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage()
        simpleMailMessage.setTo("test@test.com")
        simpleMailMessage.setSubject("test")


        simpleMailMessage.setText(template.make([
                firstname : "Grace",
                lastname  : "Hopper",
                accepted  : true,
                title     : 'Groovy for COBOL programmers'
        ]).toString())
        return simpleMailMessage
    }
}
