package com.example.spring.kafka.consumer.events;

import com.example.spring.kafka.consumer.consumer.events.ExampleRetryListener;
import com.example.spring.kafka.consumer.consumer.service.ExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExampleRetryListenerTest {

    @Mock
    ExampleService exampleService;

    @Mock
    Acknowledgment ack;

    @Mock
    KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    ExampleRetryListener exampleListener;

    @Test
    public void testConsumer() {
        Mockito
                .doNothing()
                .when(exampleService)
                .testName(Mockito.anyString());

        exampleListener.testConsumer("Erison", ack);

        Mockito.verify(exampleService, Mockito.times(1)).testName("Erison");
    }

    @Test
    public void testConsumerException() {
        Mockito
                .doThrow(new RuntimeException("TEST"))
                .when(exampleService)
                .testName(Mockito.anyString());

        exampleListener.testConsumer("Erison", ack);

        Mockito.verify(exampleService, Mockito.times(1)).testName("Erison");
    }

    @Test(expected = Test.None.class)
    public void testFallback() {

        when(kafkaTemplate.send(Mockito.any(), Mockito.any())).thenReturn(null);

        exampleListener.fallback("Erison", ack);
    }
}
