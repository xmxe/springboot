package com.xmxe.spring2.base.producer;

import com.alibaba.fastjson2.JSON;
import com.xmxe.spring2.common.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * kafka消息发送者
 */
//@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    /**
     *  发送文字消息
     * @param message
     */
    public String sendStr(String message){
        kafkaTemplate.send(KafkaConfig.TOPIC1,message);
        return message;
    }

    /**
     *  发送对象消息
     * @param obj
     */
    public String sendObj(Object obj){
        String message = JSON.toJSONString(obj);
        kafkaTemplate.send(KafkaConfig.TOPIC2,message);
        return message;
    }
}