package com.spring.cinemaapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    /*@Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("cursjustcodeit@gmail.com");
        mailSender.setPassword("curs1234");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return mailSender;
    }*/
}

/*
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=cursjustcodeit@gmail.com
spring.mail.properties.mail.smtp.ssl.trust=smtp.gmail.com
spring.mail.password=dyzzucfiwuknztgb
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true*/
