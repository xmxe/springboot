package com.xmxe.spring2.transaction.consumer;

import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;

//@Component
public class KafkaConsumerListener {

    private Logger log = LoggerFactory.getLogger(KafkaConsumerListener.class);
    @KafkaListener(topics = "transaction-test")
    public void onMessage2(Message message){
        Object payload = message.getPayload();
        log.info("transaction-test接收结果:{}",JSON.toJSONString(payload));
    }

}