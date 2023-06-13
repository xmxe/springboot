package com.xmxe.spring2.ack.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * AckMode 监听器
 */
@Component
public class KafkaAckModeListener {
    private Logger log = LoggerFactory.getLogger(KafkaAckModeListener.class);
    /*
     * 一次处理一条数据
     * 2020-05-04 23:17:00.942  INFO 20596 --- [ntainer#0-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : recordListenerContainerFactory 处理数据量：1
     * 2020-05-04 23:17:00.942  INFO 20596 --- [ntainer#0-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : recordListenerContainerFactory 处理数据内容：MyMessage(id=0, name=测试消息0, type=0, createTime=1588605417152)
     * 2020-05-04 23:17:00.964  INFO 20596 --- [ntainer#0-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : recordListenerContainerFactory 处理数据量：1
     * 2020-05-04 23:17:00.964  INFO 20596 --- [ntainer#0-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : recordListenerContainerFactory 处理数据内容：MyMessage(id=1, name=测试消息1, type=0, createTime=1588605417152)
     * 2020-05-04 23:17:00.983  INFO 20596 --- [ntainer#0-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : recordListenerContainerFactory 处理数据量：1
     * 2020-05-04 23:17:00.983  INFO 20596 --- [ntainer#0-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : recordListenerContainerFactory 处理数据内容：MyMessage(id=2, name=测试消息2, type=0, createTime=1588605417152)
     */

    /**
     * RECORD   当每一条记录被消费者监听器（ListenerConsumer）处理之后提交
     * @param message
     */
    @KafkaListener(containerFactory = "recordListenerContainerFactory" , topics = "kafka-record")
    public void onMessageRecord(List<Object> message){
        log.info("recordListenerContainerFactory 处理数据量：{}",message.size());
        message.forEach(item -> log.info("recordListenerContainerFactory 处理数据内容：{}",item));
    }

    /**
     * MANUAL_IMMEDIATE 手动调用Acknowledgment.acknowledge()后立即提交
     * @param message
     */
    @KafkaListener(containerFactory = "manualImmediateListenerContainerFactory" , topics = "kafka-manualImmediate")
    public void onMessageManualImmediate(List<Object> message, Acknowledgment ack){
        log.info("manualImmediateListenerContainerFactory 处理数据量：{}",message.size());
        message.forEach(item -> log.info("manualImmediateListenerContainerFactory 处理数据内容：{}",item));
        ack.acknowledge();//直接提交offset
    }

    /**
     * MANUAL   当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后, 手动调用Acknowledgment.acknowledge()后提交
     * @param message
     * @param ack
     */
    @KafkaListener(containerFactory = "manualListenerContainerFactory" , topics = "kafka-manual")
    public void onMessageManual(List<Object> message, Acknowledgment ack){
        log.info("manualListenerContainerFactory 处理数据量：{}",message.size());
        message.forEach(item -> log.info("manualListenerContainerFactory 处理数据内容：{}",item));
        ack.acknowledge();//直接提交offset
    }


    /**
     * COUNT_TIME   TIME |　COUNT　有一个条件满足时提交
     * @param message
     */
    // @KafkaListener(containerFactory = "countTimeListenerContainerFactory" , topics = "kafka-countTime")
    public void onMessageCountTime(List<Object> message){
        log.info("countTimeListenerContainerFactory 处理数据量：{}",message.size());
        message.forEach(item -> log.info("countTimeListenerContainerFactory 处理数据内容：{}",item));
    }

    /*
     * 2020-05-05 00:01:44.559  INFO 15176 --- [ntainer#1-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : countListenerContainerFactory 处理数据量：1
     * 2020-05-05 00:01:44.559  INFO 15176 --- [ntainer#1-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : countListenerContainerFactory 处理数据内容：MyMessage(id=5, name=测试消息5, type=0, createTime=1588608092036)
     * 6条数据只有前5条被消费
     */

    /**
     * COUNT    当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，被处理record数量大于等于COUNT时提交
     * @param message
     */
    @KafkaListener(containerFactory = "countListenerContainerFactory" , topics = "kafka-count")
    public void onMessageCount(List<Object> message){
        log.info("countListenerContainerFactory 处理数据量：{}",message.size());
        message.forEach(item -> log.info("countListenerContainerFactory 处理数据内容：{}",item));
    }


    /*
     * 2020-05-05 00:03:19.166  INFO 22212 --- [ntainer#3-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : timeListenerContainerFactory 处理数据量：1
     * 2020-05-05 00:03:19.166  INFO 22212 --- [ntainer#3-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : timeListenerContainerFactory 处理数据内容：MyMessage(id=0, name=测试消息0, type=0, createTime=1588608188129)
     * 2020-05-05 00:03:19.167  INFO 22212 --- [ntainer#3-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : timeListenerContainerFactory 处理数据量：1
     * 2020-05-05 00:03:19.167  INFO 22212 --- [ntainer#3-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : timeListenerContainerFactory 处理数据内容：MyMessage(id=1, name=测试消息1, type=0, createTime=1588608188129)
     * 2020-05-05 00:03:19.168  INFO 22212 --- [ntainer#3-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : timeListenerContainerFactory 处理数据量：1
     * 2020-05-05 00:03:19.168  INFO 22212 --- [ntainer#3-0-C-1] d.s.k.ack.consumer.KafkaAckModeListener  : timeListenerContainerFactory 处理数据内容：MyMessage(id=2, name=测试消息2, type=0, createTime=1588608188129)
     * 发送消息后马上关闭则消息无法被消费
     */
    /**
     * TIME     当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，距离上次提交时间大于TIME时提交
     * @param message
     */
    @KafkaListener(containerFactory = "timeListenerContainerFactory" , topics = "kafka-time")
    public void onMessageTime(List<Object> message){
        log.info("timeListenerContainerFactory 处理数据量：{}",message.size());
        message.forEach(item -> log.info("timeListenerContainerFactory 处理数据内容：{}",item));
    }

    /**
     * BATCH    当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后提交
     * @param consumerRecords
     */
    //@KafkaListener(containerFactory = "batchListenerContainerFactory" , topics = "kafka-batch")
    public void onMessageBatch(List<ConsumerRecord> consumerRecords){
        Iterator<ConsumerRecord> iterator = consumerRecords.iterator();
        while (iterator.hasNext()) {
            handlerMessage(iterator.next());
        }
        // ack.acknowledge();//直接提交offset
    }


    private void handlerMessage(ConsumerRecord consumerRecord) {
        String key = consumerRecord.key().toString();
        Object value = consumerRecord.value();
        String topic = consumerRecord.topic();
        if (value == null) {
            return;
        }
        log.info("topic:{},接收结果:{}",topic,key);
    }
}