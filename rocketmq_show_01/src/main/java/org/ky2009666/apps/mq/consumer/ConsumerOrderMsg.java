package org.ky2009666.apps.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.ky2009666.apps.mq.constant.MqConstant;

import java.util.List;

/**
 * 顺序消费消息，同一个队列中的消息顺序消费
 *
 * @author Lenovo
 */
@Slf4j
public class ConsumerOrderMsg {
    /**
     * 主入口方法
     *
     * @param args 参数
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {
        //1、创建消息的消费者，并指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MqConstant.MQ_PRODUCER_CONSUMER_GROUP_NAME_01);
        //2、指定消息队列的服务器地址
        consumer.setNamesrvAddr(MqConstant.MQ_NAME_SERV_ADDR);
        //3、订阅主题和TAG
        consumer.subscribe(MqConstant.MQ_TOPIC_01, MqConstant.MQ_TAG_01);
        //4、配置消息的监听器
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt msg : msgs
                ) {
                    log.info("当前线程名称:{},msgs:{}", Thread.currentThread().getName(), new String(msg.getBody()));
                }

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //5、启动消息队列消费者
        consumer.start();
    }
}
