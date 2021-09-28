package com.example.spring.kafka.consumer.consumer.events;

import com.example.spring.kafka.consumer.consumer.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@EnableScheduling
@Slf4j
public class DlqExampleRetryListener {
    @Autowired
    ExampleService exampleService;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaListenerEndpointRegistry dlqRegistry;

    @Value("${kafka.bootstrap.group-id-dlq}")
    private String groupIdDlq;

    @KafkaListener(topics = "{kafka.bootstrap.topic-dlq}", autoStartup = "false",
            groupId = "${kafka.bootstrap.group-id-dlq}", containerFactory = "dlqKafkaListenerContainerFactory")
    public void dlqTestConsumer(String message, Acknowledgment ack) {
        try {
            exampleService.testName(message);
        } catch (Exception e) {
            log.error("Error. " + e.getMessage());
        } finally {
            ack.acknowledge();
        }
    }

    @Scheduled(cron = "${app.consumer.dlq.start.cron}")
    public void startListener() {
        log.info("Starting DLQ Consumer");

        var listenerId = getListenerId();
        dlqRegistry.getListenerContainer(listenerId).start();
    }

    @Scheduled(cron = "${app.consumer.dlq.stop.cron}")
    public void stopListener() {
        log.info("Stopping DLQ Consumer");

        var listenerId = getListenerId();
        dlqRegistry.getListenerContainer(listenerId).stop();
    }

    public String getListenerId() {
        return dlqRegistry.getAllListenerContainers().stream()
                .filter(listener -> listener.getGroupId().equalsIgnoreCase(groupIdDlq))
                .collect(Collectors.toList()).get(0).getListenerId();
    }
}
