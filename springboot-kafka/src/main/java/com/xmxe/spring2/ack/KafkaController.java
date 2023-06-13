package com.xmxe.spring2.ack;

import com.xmxe.spring2.ack.producer.KafkaAckSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class KafkaController {

    @Autowired
    private KafkaAckSender send;

    @GetMapping("/ack/sendStr/{topic}/{max}")
    public String sendStrMax(
            @PathVariable("topic") String topic,
            @PathVariable("max") int max){
        String s = send.sendStr("测试消息",topic,max);
        return s;
    }
}
