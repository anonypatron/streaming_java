package com.kafka.service.kafka;

import com.kafka.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaLogProducer {

    private final KafkaTemplate<String, Log> kafkaTemplate;

    @Autowired
    public KafkaLogProducer(KafkaTemplate<String, Log> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToKafka(String topic, Log log) {
        System.out.println("sendToKafka");
        kafkaTemplate.send(topic, log);
    }

}
