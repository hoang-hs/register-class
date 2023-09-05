package com.example.registerclass.present.consumer;


import com.example.registerclass.core.domain.Registration;
import com.example.registerclass.core.service.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;

@Component
public class RegistrationConsumer implements Runnable {
    private final KafkaConsumer<String, String> consumer;
    @Value("${kafka.consumer.registration.topic}")
    private String topic;
    @Value("${kafka.consumer.registration.group-id}")
    private String groupId;
    @Value("${kafka.host}")
    String BOOTSTRAP_SERVERS_CONFIG;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationConsumer(RegistrationService registrationService) {
        this.registrationService = registrationService;
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.24.32.11:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, String.valueOf(false));
        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("register-class"));
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(2);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("We received message: " + record.value() + " from topic: " + record.topic());
                try {
                    Registration registration = objectMapper.readValue(record.value(), Registration.class);
                    if (registrationService.handleRegisterClass(registration)) {
                        consumer.commitSync();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

    }

}
