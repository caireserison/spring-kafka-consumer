package com.example.spring.kafka.consumer.consumer.events;

import com.example.spring.kafka.consumer.consumer.entity.ObjectExample;
import com.example.spring.kafka.consumer.consumer.service.ComplexObjectExampleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ComplexObjectExampleListener {

    @Autowired
    ComplexObjectExampleService complexObjectExampleService;

    public ComplexObjectExampleListener() {
        super();
    }

    @KafkaListener(topics = "${kafka.bootstrap.topic-complex}")
    public void consumerObject(String json) {
        try {
            // Configuração necessária para tratar problema do LocalDate no momento da conversão de json para o objeto
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());

            // Convertendo json para o objeto (evita maiores problemas)
            ObjectExample objectExample = objectMapper.readValue(json, ObjectExample.class);

            complexObjectExampleService.ageCalculate(objectExample);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}
