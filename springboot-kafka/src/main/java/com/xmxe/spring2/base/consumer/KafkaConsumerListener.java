package com.xmxe.spring2.base.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

//@Component
public class KafkaConsumerListener {
    private Logger log = LoggerFactory.getLogger(KafkaConsumerListener.class);
    @KafkaListener(topics = "kafka-topic1")
    public void onMessage1(String message){
        System.out.println(message);
        log.info("kafka-topic1接收结果:{}",message);
    }

    @KafkaListener(topics = "kafka-topic2")
    public void onMessage2(String message){
        System.out.println(message);
        log.info("kafka-topic2接收结果:{}",message);
    }
}
