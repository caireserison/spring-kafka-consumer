package com.example.spring.kafka.consumer.events;

import com.example.spring.kafka.consumer.consumer.events.ExampleListener;
import com.example.spring.kafka.consumer.consumer.service.ExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExampleListenerTest {

    @Mock
    ExampleService exampleService;

    @InjectMocks
    ExampleListener exampleListener;

    @Test
    public void testConsumer() {
        Mockito
                .doNothing()
                .when(exampleService)
                .testName(Mockito.anyString());

        exampleListener.testConsumer("Erison");

        Mockito.verify(exampleService, Mockito.times(1)).testName("Erison");
    }

    @Test
    public void testConsumerException() {
        Mockito
                .doThrow(new RuntimeException("TEST"))
                .when(exampleService)
                .testName(Mockito.anyString());

        exampleListener.testConsumer("Erison");

        Mockito.verify(exampleService, Mockito.times(1)).testName("Erison");
    }
}
