package com.example.spring.kafka.consumer.consumer.service;

import com.example.spring.kafka.consumer.consumer.entity.ObjectExample;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class ComplexObjectExampleService {
    public void ageCalculate(ObjectExample objectExample) {
        LocalDate today = LocalDate.now();
        Period range = Period.between(objectExample.getDateBirth(), today);
        Integer age = range.getYears();

        System.out.println(objectExample.getName() + " is " + age + " years old.");
    }
}
