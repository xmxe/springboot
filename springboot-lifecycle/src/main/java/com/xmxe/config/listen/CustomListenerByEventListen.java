package com.xmxe.config.listen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
/**
 * 除了继承ApplicationEvent之外还可以通过注解@EventListener实现监听，
 * 也可以在application.properties配置context.listener.classes=com.xmxe.config.listen.MyEvent指定监听类
 */
@Component
public class CustomListenerByEventListen{

    Logger logger = LoggerFactory.getLogger(CustomListenerByEventListen.class);

    @EventListener
    @Async
    public void send(MyEvent2 myEvent) {
        logger.info("监听到自定义事件了2--{}",myEvent.msg());
    }
}