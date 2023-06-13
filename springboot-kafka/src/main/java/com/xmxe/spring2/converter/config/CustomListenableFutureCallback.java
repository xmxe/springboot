package com.xmxe.spring2.converter.config;

import com.alibaba.fastjson2.JSON;
import com.xmxe.spring2.common.entity.CustomMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * 获取消费的异步结果
 */
public class CustomListenableFutureCallback implements ListenableFutureCallback<SendResult<String, Object>> {
    private Logger log = LoggerFactory.getLogger(CustomListenableFutureCallback.class);
    public CustomListenableFutureCallback(CustomMessage customMessage) {
        this.customMessage = customMessage;
    }

    private CustomMessage customMessage;

    /**
     * 失败的时候
     * @param throwable
     */
    @Override
    public void onFailure(Throwable throwable) {
        log.error("执行了onFailure");
        log.error("topic:{},message:{}" ,
            customMessage.getPayload(),
            JSON.toJSONString(customMessage.getPayload()));
    }

    /**
     * 成功的办法
     * @param stringMyMessageSendResult
     */
    @Override
    public void onSuccess(SendResult<String, Object> stringMyMessageSendResult) {
        log.info("执行了onSuccess");
        log.info("topic:{},message:{}" ,
            customMessage.getPayload(),
            JSON.toJSONString(customMessage.getPayload()));
    }
}