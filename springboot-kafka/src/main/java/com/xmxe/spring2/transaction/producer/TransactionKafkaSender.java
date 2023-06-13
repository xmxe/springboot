package com.xmxe.spring2.transaction.producer;

import com.alibaba.fastjson.JSON;
import com.xmxe.spring2.common.entity.MyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.ExecutionException;

/**
 * 事务性的消息发送
 */
//@Component
public class TransactionKafkaSender {

    private Logger log = LoggerFactory.getLogger(TransactionKafkaSender.class);
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    /**
     * 事务性的消息发送
     * @param myMessage
     */
    public String sendInTransaction(MyMessage myMessage){

        for (int i = 0; i < 5; i++) {
            int index = i;
            myMessage.setId(i);
            kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback<String, Object, Object>() {
                @Override
                public Object doInOperations(KafkaOperations<String, Object> operations) {
                    try {
                        SendResult<String, Object> result = null;
                        if (index == 3) {
                            throw new RuntimeException();
                        }
                        try {
                            result = kafkaTemplate.send("transaction-test", "测试数据：" + index).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        log.info("kafka 事务消息: {}" , "测试数据：" + index);
                        return JSON.toJSONString(myMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "发送失败";
                    }
                }
            });
        }
        return JSON.toJSONString(myMessage);
    }

}