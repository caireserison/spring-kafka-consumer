package com.example.spring.kafka.consumer.service;

import com.example.spring.kafka.consumer.consumer.entity.ObjectExample;
import com.example.spring.kafka.consumer.consumer.service.ComplexObjectExampleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ComplexObjectExampleServiceTest {

    @Mock
    ComplexObjectExampleService complexObjectExampleService;

    ObjectExample objectExample;

    @Before
    public void setup() {
        objectExample = new ObjectExample();
        objectExample.setId(1L);
        objectExample.setName("Erison");
        objectExample.setDateBirth(LocalDate.now().minusYears(30));
    }

    @Test
    public void testAgeCalculate() {
        complexObjectExampleService.ageCalculate(objectExample);

        Mockito.verify(complexObjectExampleService, Mockito.times(1)).ageCalculate(objectExample);
    }
}
