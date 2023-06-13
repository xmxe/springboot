package com.xmxe.spring2.common.entity;


import org.springframework.messaging.MessageHeaders;


public class CustomMessage implements SamplesMessage {

    private Object payLoad;

    private String topic;

    public void setPayload(Object payLoad,String topic) {
        this.payLoad = payLoad;
        this.topic = topic;
    }

    @Override
    public String getTopic() {
        return this.topic;
    }

    @Override
    public Object getPayload() {
        return this.payLoad;
    }

    @Override
    public MessageHeaders getHeaders() {
        return new MessageHeaders(null);
    }

    public Object getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(Object payLoad) {
        this.payLoad = payLoad;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
