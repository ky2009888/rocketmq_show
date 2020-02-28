package org.ky2009666.apps.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听者
 *
 * @author Lenovo
 */
@RocketMQMessageListener(topic = "base", consumerGroup = "${rocketmq.consumer.group}")
@Slf4j
@Component
public class Consumer01 implements RocketMQListener<String> {
    /**
     * 进行消息监听
     *
     * @param content 消息内容
     */
    public void onMessage(String content) {
        log.info("监听到的消息内容:{}", content);
    }
}
