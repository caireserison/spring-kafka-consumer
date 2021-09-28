package com.example.spring.kafka.consumer.consumer.events;

import com.example.spring.kafka.consumer.consumer.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExampleRetryListener {

	@Autowired
	ExampleService exampleService;

	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;

	@Value("{kafka.bootstrap.topic-dlq}")
	private String dlqTopic;

	public ExampleRetryListener() {
		super();
	}

	@CircuitBreaker(openTimeoutExpression = "3000}",
			resetTimeoutExpression = "20000",
			maxAttemptsExpression = "3")
	@KafkaListener(topics = "${kafka.bootstrap.topic}")
	public void testConsumer(String message, Acknowledgment ack) {
		try {
			exampleService.testName(message);
		} catch (Exception e) {
			log.error("Erro: " + e.getMessage());
		} finally {
			ack.acknowledge();
		}
	}

	@Recover
	public void fallback(String message, Acknowledgment ack) {
		log.info("Recebendo mensagem no método fallback");
		log.info("Enviando a mensagem para a fila dlq {}.", dlqTopic);
		kafkaTemplate.send(dlqTopic, message);
	}
}
