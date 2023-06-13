package com.xmxe.spring2.converter.producer;

import com.alibaba.fastjson.JSON;
import com.xmxe.spring2.common.entity.CustomMessage;
import com.xmxe.spring2.common.entity.MyMessage;
import com.xmxe.spring2.converter.config.CustomListenableFutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//@Component
public class KafkaSender {
    private Logger log = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    /**
     * 发送消息并获得结果
     * @param myMessage
     * @param topic
     */
    public String send(MyMessage myMessage,
                       String topic) {
        CustomMessage message = new CustomMessage();
        message.setPayload(myMessage,topic);
        kafkaTemplate.send(message);
        return JSON.toJSONString(message);
    }

    /**
     * 异步的获得结果
     * @param myMessage
     * @param topic
     */
    @Async
    public String sendSync(MyMessage myMessage, String topic) {
        CustomMessage message = new CustomMessage();
        message.setPayload(myMessage,topic);
        try {
            // 关于同步获取消息结果，get方法设置了一个很短的消息获取超时时间，这样可以模拟消息获取超时报错
            SendResult<String, Object> stringObjectSendResult = kafkaTemplate.send(message).get(100,TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(message);
    }

    /**
     * 异步的获得结果
     * @param myMessage
     * @param topic
     */
    @Async
    public String sendAsync(MyMessage myMessage, String topic) {
        CustomMessage message = new CustomMessage();
        message.setPayload(myMessage,topic);
        ListenableFuture<SendResult<String, Object>> send = kafkaTemplate.send(message);
        log.info("Async消息已经发送,时间戳:{}",System.currentTimeMillis());
        send.addCallback(new CustomListenableFutureCallback(message));
        return JSON.toJSONString(message);
    }

}