package com.xmxe.spring2.transaction;

import com.xmxe.spring2.common.entity.MyMessage;
import com.xmxe.spring2.transaction.producer.TransactionKafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用来测试的web请求
 */
//@RestController
public class KafkaController {

    @Autowired
    private TransactionKafkaSender transactionKafkaSender;

    /**
     * 事务消息发送
     */
    @GetMapping("/transaction/send")
    public String sendTransaction(){
        MyMessage myMessage = new MyMessage();
        myMessage.setId(3);
        myMessage.setName("sendTransaction");
        myMessage.setType(3);
        String rest = transactionKafkaSender.sendInTransaction(myMessage);
        return rest;
    }

}