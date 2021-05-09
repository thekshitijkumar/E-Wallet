package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    @Autowired
    SimpleMailMessage simpleMailMessage;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    ObjectMapper objectMapper;
    @KafkaListener(topics = {"send_email"},groupId = "test123")
    public void sendMail(String msg) throws JsonProcessingException {
        JSONObject emailRequest=objectMapper.readValue(msg,JSONObject.class);
        simpleMailMessage.setText((String)emailRequest.get("message"));
        simpleMailMessage.setTo((String) emailRequest.get("toUser"));
        simpleMailMessage.setSubject("Transaction Update");
        simpleMailMessage.setFrom("random@gmao.com");

        javaMailSender.send(simpleMailMessage);

    }

}
