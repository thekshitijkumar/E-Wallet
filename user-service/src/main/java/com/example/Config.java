package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Properties;

@Configuration
public class Config {

    //Redis Config

    @Bean
    JedisConnectionFactory getConnectionFactory()
    {
        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration();
        JedisConnectionFactory jedisConnectionfactory=new JedisConnectionFactory(redisStandaloneConfiguration);
        return jedisConnectionfactory;
    }
    @Bean
    RedisTemplate<String,Object> redisTemplate()
    {
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        RedisSerializer<String> stringRedisSerializer=new StringRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer=new JdkSerializationRedisSerializer();

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setConnectionFactory(getConnectionFactory());

        return redisTemplate;

    }
    //kafka Config
    @Bean
    Properties kafkaProperties()
    {
        Properties properties=new Properties();
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        return properties;
    }
    @Bean
    ProducerFactory<String,String> getproducerFactory()
    {
        return new DefaultKafkaProducerFactory(kafkaProperties());
    }
    @Bean
    KafkaTemplate<String,String> getKafkatemplate()
    {
        return new KafkaTemplate<>(getproducerFactory());
    }


    //object mapper
    @Bean
    ObjectMapper getObjectMapper()
    {
        return new ObjectMapper();
    }

}
