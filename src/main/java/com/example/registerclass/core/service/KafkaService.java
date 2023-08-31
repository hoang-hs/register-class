package com.example.registerclass.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {
    private final KafkaProducer<String, String> producer;
    private final ObjectWriter ow;

    @Autowired
    public KafkaService(KafkaProducer<String, String> producer) {
        this.producer = producer;
        this.ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    public void send(String topic, Object msg) {
        String value;
        try {
            value = ow.writeValueAsString(msg);
        } catch (JsonProcessingException exp) {
            log.error("send msg error,msg: {}, exp:{}", msg, exp.getStackTrace());
            return;
        }
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, value);
        producer.send(record);
    }
}
