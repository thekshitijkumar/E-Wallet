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

import java.util.Properties;

@Configuration
public class Config {

    //kafka common configurations

    @Bean
    Properties kafkaProperties()
    {
        Properties properties=new Properties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        return properties;
    }
    //kafka producer factory

    @Bean
    ProducerFactory<String,String> getProducerFactory()
    {
        return new DefaultKafkaProducerFactory(kafkaProperties());
    }

    @Bean
    KafkaTemplate<String,String> getKafkatemplate()
    {
        return new KafkaTemplate<>(getProducerFactory());
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
}

