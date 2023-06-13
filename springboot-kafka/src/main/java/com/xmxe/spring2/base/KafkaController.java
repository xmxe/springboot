package com.xmxe.spring2.base;

import com.xmxe.spring2.base.producer.KafkaAdminManager;
import com.xmxe.spring2.base.producer.KafkaSender;
import com.xmxe.spring2.common.entity.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 基础的kafka操作
 */
//@RestController
public class KafkaController {

    /**
     * kafka消息生产
     */
    @Autowired
    private KafkaSender send;

    /**
     * kafka操作
     */
    @Autowired
    private KafkaAdminManager admin;

    /**
     * 创建topic
     * @param topic
     */
    @GetMapping("/topic/add/{topic}")
    public String topicAdd(@PathVariable("topic")String topic){
        String s = admin.createTopic(topic);
        return s;
    }


    /**
     * 创建topic
     * @param topic
     */
    @GetMapping("/topic/query/{topic}")
    public String topicQuery(@PathVariable("topic")String topic){
        String s = admin.queryTopic(topic);
        return s;
    }

    /**
     * 删除topic
     * @param topic
     */
    @GetMapping("/topic/delete/{topic}")
    public String topicDelete(@PathVariable("topic")String topic){
        String s = admin.deleteTopic(topic);
        return s;
    }

    /**
     * 发送消息
     */
    @GetMapping("/message/sendStr")
    public String sendStr(){
        String s = send.sendStr("测试消息");
        return s;
    }

    /**
     * 发送消息
     */
    @GetMapping("/message/sendObj")
    public String sendObj(){
        MyMessage myMessage = new MyMessage();
        myMessage.setId(100L);
        myMessage.setName("message对象");
        myMessage.setType(2);
        String s = send.sendObj(myMessage);
        return s;
    }

}