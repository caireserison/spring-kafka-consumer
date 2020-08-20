package com.example.spring.kafka.consumer.consumer.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.spring.kafka.consumer.consumer.service.ExampleService;

@Component
public class ExampleListener {

	@Autowired
	ExampleService exampleService;
	
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;
	
	public ExampleListener() {
		super();
	}
	
	@KafkaListener(topics = "${kafka.bootstrap.topic}")
	public void testConsumer(String message) {
		try {
			exampleService.testName(message);
		} catch (Exception e) {
			System.out.println("Error. Reprocess? ...DLQ");
		}
	}
}
