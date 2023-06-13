package com.xmxe.spring2.ack.producer;

import com.xmxe.spring2.common.entity.CustomMessage;
import com.xmxe.spring2.common.entity.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * kafka消息发送者
 */
@Component
public class KafkaAckSender {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    public String sendStr(String messageStr,String topic,int max){
        long l = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            MyMessage myMessage = new MyMessage();
            myMessage.setId(i);
            myMessage.setName(messageStr + i);
            myMessage.setCreateTime(l);
            CustomMessage message = new CustomMessage();
            message.setPayload(myMessage,topic);
            kafkaTemplate.send(message);
        }
        return messageStr;
    }


}
