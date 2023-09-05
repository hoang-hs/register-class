//package com.example.registerclass.present.consumer;
//
//import com.example.registerclass.core.domain.Registration;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//@Log4j2
//@Component
//public class Consumer {
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.topic.group}")
//    public void consume(@Payload String payload,
//                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
//                        @Header(KafkaHeaders.OFFSET) Long offset) {
//        Registration registration;
//        try {
//            registration = objectMapper.readValue(payload, Registration.class);
//        } catch (Exception e) {
//            registration = null;
//            e.printStackTrace();
//        }
//        log.info("Received a message: {} | topic {} | partition {} | offset {}", user, topic, partition, offset);
//    }
//
//}
