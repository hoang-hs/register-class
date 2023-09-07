package com.example.registerclass.present.consumer;


import com.example.registerclass.core.domain.Registration;
import com.example.registerclass.core.service.ConsumerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Log4j2
@RequiredArgsConstructor
public class RegistrationConsumer implements Runnable {
    private KafkaConsumer<String, String> consumer;
    @Value("${kafka.consumer.registration.register-topic}")
    private String registerTopic;
    @Value("${kafka.consumer.registration.cancel-topic}")
    private String cancelTopic;
    @Value("${kafka.consumer.registration.group-id}")
    private String groupId;
    private final BaseConsumer baseConsumer;
    private final ConsumerService consumerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void init() {
        Collection<String> topics = Arrays.asList(registerTopic, cancelTopic);
        this.consumer = baseConsumer.buildConsumer(topics, groupId);
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(2);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("We received message: " + record.value() + " from topic: " + record.topic());
                try {
                    Registration registration = objectMapper.readValue(record.value(), Registration.class);
                    consumerService.handleRecord(registration);
                    consumer.commitSync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shutdown() {
        consumer.close();
    }

}
