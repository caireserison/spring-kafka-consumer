package com.example.spring.kafka.consumer.service;

import com.example.spring.kafka.consumer.consumer.service.ExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExampleServiceTest {

    @Mock
    ExampleService exampleService;

    @Test
    public void testName() {
        exampleService.testName("Erison");

        Mockito.verify(exampleService, Mockito.times(1)).testName("Erison");
    }
}
