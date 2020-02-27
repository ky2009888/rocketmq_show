package org.ky2009666.apps.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.ky2009666.apps.mq.constant.MqConstant;

import java.util.List;

/**
 * 消息的消费者,进行消息的消费
 *
 * @author Lenovo
 */
@Slf4j
public class Consumer01 {
    public static void main(String[] args) throws Exception {
        //1、创建消息的消费者，指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MqConstant.MQ_PRODUCER_CONSUMER_GROUP_NAME_01);
        //2、指定消息队列服务器的地址
        consumer.setNamesrvAddr(MqConstant.MQ_NAME_SERV_ADDR);
        //3、订阅主题和tag
        consumer.subscribe(MqConstant.MQ_TOPIC_01, MqConstant.MQ_TAG_01);
        //4、设置回调函数，处理函数
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            /**
             * 接收消息内容的方法
             * @param msgs 消息
             * @param context 上下文
             * @return 返回内容
             */
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                log.info("msg:{}", msgs);
                //处理消息
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5、启动消费者
        consumer.start();
    }
}
