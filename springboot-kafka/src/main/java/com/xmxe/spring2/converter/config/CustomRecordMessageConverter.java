package com.xmxe.spring2.converter.config;

import com.alibaba.fastjson.JSON;
import com.xmxe.spring2.common.config.KafkaConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.messaging.Message;

import java.lang.reflect.Type;

/**
 * 自定义消息转换器
 */
public class CustomRecordMessageConverter implements RecordMessageConverter {
    private Logger log = LoggerFactory.getLogger(CustomRecordMessageConverter.class);
    /**
     * 负责处理消费端传递的内容
     * @param consumerRecord
     * @param acknowledgment
     * @param consumer
     * @param type
     */
    @Override
    public Message<?> toMessage(ConsumerRecord<?, ?> consumerRecord,
                                Acknowledgment acknowledgment,
                                Consumer<?, ?> consumer,
                                Type type) {
        log.info("执行了………………toMessage");
        log.info("consumerRecord 内容：{}", JSON.toJSONString(consumerRecord));

        log.info("acknowledgment 内容：{}", JSON.toJSONString(acknowledgment));

        log.info("consumer 内容：{}", JSON.toJSONString(consumer));

        log.info("type 内容：{}", JSON.toJSONString(type));

        return null;
    }

    /**
     * 负责将生产者的消息进行处理
     * @param message
     * @param s
     */
    @Override
    public ProducerRecord<?, ?> fromMessage(Message<?> message, String s) {
        log.info("执行了………………fromMessage");
        log.info("acknowledgment 内容：{}", JSON.toJSONString(message));

        log.info("consumer 内容：{}", s);
        String valueStr = JSON.toJSONString(message.getPayload());
        // 此处在对消息处理的时候可以尝试修改消息目标
        ProducerRecord record = new ProducerRecord(KafkaConfig.TOPIC2,valueStr);

        return record;
    }
}