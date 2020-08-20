package com.example.spring.kafka.consumer.consumer.service;

import org.springframework.stereotype.Component;

@Component
public class ExampleService {
	public void testName(String name) {
		System.out.println(name);
	}
}
