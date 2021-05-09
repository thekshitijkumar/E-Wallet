package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class Config {

    //kafka common configurations

    @Bean
    Properties kafkaProperties()
    {
        Properties properties=new Properties();

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        return properties;
    }


    //kafka consumer factory
    @Bean
    ConsumerFactory<String,String> consumerFactory()
    {
        return new DefaultKafkaConsumerFactory(kafkaProperties());
    }

    //this is required by Kafkalistener
    @Bean
    ConcurrentKafkaListenerContainerFactory<String,String> concurrentKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory kafakConcurrentKafkaListenerContainerFactory=new ConcurrentKafkaListenerContainerFactory();
        kafakConcurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        return kafakConcurrentKafkaListenerContainerFactory;
    }

    //object mapper
    @Bean
    ObjectMapper getObjectMapper()
    {
        return new ObjectMapper();
    }

    @Bean
    RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
    //email related Config

    @Bean
    JavaMailSender javaMailSender()
    {
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("random@gmail.com");//your email
        javaMailSender.setPassword("randomPasswd");//your passwd


        Properties properties=javaMailSender.getJavaMailProperties();
        properties.put("mail.smtp.start tls.enable","true");
        properties.put("mail.debug","true");

        return javaMailSender;
    }

    @Bean
    SimpleMailMessage simpleMailMessage()
    {
        return new SimpleMailMessage();
    }
}

