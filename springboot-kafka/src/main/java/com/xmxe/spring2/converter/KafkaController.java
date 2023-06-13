package com.xmxe.spring2.converter;

import com.xmxe.spring2.common.config.KafkaConfig;
import com.xmxe.spring2.common.entity.MyMessage;
import com.xmxe.spring2.converter.producer.KafkaSender;
import com.xmxe.spring2.transaction.producer.TransactionKafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

/**
 * 用来测试的web请求
 */
//@RestController
public class KafkaController {

    @Autowired
    private KafkaSender send;

    @Autowired
    private TransactionKafkaSender transactionKafkaSender;

    /**
     * 消息转换的测试
     */
    @GetMapping("/converter/send")
    public String send(){
        MyMessage myMessage = new MyMessage();
        myMessage.setId(1);
        String rest = send.send(myMessage, KafkaConfig.TOPIC1);
        return rest;
    }

    /**
     * 同步的消息内容
     */
    @GetMapping("/sync/send")
    public String sendSync(){
        MyMessage myMessage = new MyMessage();
        myMessage.setId(2);
        myMessage.setName("sendSync");
        myMessage.setType(2);
        Random random = new Random();
        String rest = send.sendSync(myMessage, random.nextBoolean() ? KafkaConfig.TOPIC1:KafkaConfig.TOPIC2);
        return rest;
    }

    /**
     * 异步的消息内容
     */
    @GetMapping("/async/send")
    public String sendAsync(){
        MyMessage myMessage = new MyMessage();
        myMessage.setId(2);
        myMessage.setName("sendAsync");
        myMessage.setType(2);
        Random random = new Random();
        String rest = send.sendAsync(myMessage, random.nextBoolean() ? KafkaConfig.TOPIC1:KafkaConfig.TOPIC2);
        return rest;
    }

}