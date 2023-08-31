package com.example.registerclass.present.consumer;


import com.example.registerclass.core.domain.Registration;
import com.example.registerclass.core.service.RegistrationService;
import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.Properties;

public class RegistrationConsumer extends Thread {
    private final KafkaConsumer<String, String> consumer;
    @Value("${kafka.consumer.registration.topic}")
    private String topic;
    @Value("${kafka.consumer.registration.group-id}")
    private String groupId;
    @Value("${kafka.host}")
    String BOOTSTRAP_SERVERS_CONFIG;

    private final RegistrationService registrationService;

    public RegistrationConsumer(RegistrationService registrationService) {
        this.registrationService = registrationService;
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("register-class"));
        this.consumer = consumer;
    }

    public void run() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(2);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("We received message: " + record.value() + " from topic: " + record.topic());
                Gson gson = new Gson();
                Registration registration = gson.fromJson(record.value(), Registration.class);
                if (registrationService.handleRegisterClass(registration)) {
                    consumer.commitSync();
                }
            }


        }

    }

}
