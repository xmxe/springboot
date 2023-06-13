package com.xmxe.spring2.converter.consumer;

import com.alibaba.fastjson2.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;

/*@Component*/
public class KafkaConsumerListener {

    private Logger log = LoggerFactory.getLogger(KafkaConsumerListener.class);
    @KafkaListener(topics = "kafka-topic1")
    public void onMessage1(Message message){
        Object payload = message.getPayload();
        log.info("kafka-topic1接收结果:{}",JSON.toJSONString(payload));
    }


    @KafkaListener(topics = "kafka-topic2")
    public void onMessage2(Message message){
        Object payload = message.getPayload();
        log.info("kafka-topic2接收结果:{}",JSON.toJSONString(payload));
    }

    @KafkaListener(groupId = "consumerGroup2",topics = "kafka-transaction")
    public void consume2(ConsumerRecords<Object,String> consumerRecords){
        for (TopicPartition topicPartition:consumerRecords.partitions()){
            for (ConsumerRecord<Object,String> consumerRecord:consumerRecords.records(topicPartition)){
                System.out.println("消费时间："+System.currentTimeMillis()+" "+consumerRecord.value());
            }
        }
    }

}