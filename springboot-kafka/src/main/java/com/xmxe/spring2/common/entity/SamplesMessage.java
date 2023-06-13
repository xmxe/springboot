package com.xmxe.spring2.common.entity;

import org.springframework.messaging.Message;

public interface SamplesMessage extends Message {

    String getTopic();
}
