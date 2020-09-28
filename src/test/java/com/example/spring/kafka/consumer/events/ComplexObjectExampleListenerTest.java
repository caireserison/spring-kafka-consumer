package com.example.spring.kafka.consumer.events;

import com.example.spring.kafka.consumer.consumer.entity.ObjectExample;
import com.example.spring.kafka.consumer.consumer.events.ComplexObjectExampleListener;
import com.example.spring.kafka.consumer.consumer.service.ComplexObjectExampleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ComplexObjectExampleListenerTest {

    @Mock
    ComplexObjectExampleService complexObjectExampleService;

    @InjectMocks
    ComplexObjectExampleListener complexObjectExampleListener;

    String json;
    ObjectExample objectExample;

    @Before
    public void setup() {
        LocalDate dateBirth = LocalDate.now().minusYears(30);

        json = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Erison\",\n" +
                "    \"dateBirth\": \"" + dateBirth.toString() + "\"\n" +
                "}";

        objectExample = new ObjectExample();
        objectExample.setId(1L);
        objectExample.setName("Erison");
        objectExample.setDateBirth(dateBirth);
    }

    @Test
    public void testConsumerObject() {
        Mockito
                .doNothing()
                .when(complexObjectExampleService)
                .ageCalculate(Mockito.any(ObjectExample.class));

        complexObjectExampleListener.consumerObject(json);
        Mockito.verify(complexObjectExampleService, Mockito.times(1)).ageCalculate(Mockito.any(ObjectExample.class));
    }
}
