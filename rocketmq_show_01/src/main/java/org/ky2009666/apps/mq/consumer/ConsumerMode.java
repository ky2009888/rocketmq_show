package org.ky2009666.apps.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.ky2009666.apps.mq.constant.MqConstant;

import java.util.List;

/**
 * 消息的消费模式
 *
 * @author Lenovo
 */
@Slf4j
public class ConsumerMode {
    public static void main(String[] args) throws Exception {
        //1、创建消息的消费者，并指定组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MqConstant.MQ_PRODUCER_CONSUMER_GROUP_NAME_01);
        //2、指定消息的服务器地址
        consumer.setNamesrvAddr(MqConstant.MQ_NAME_SERV_ADDR);
        //3、订阅主题和TAG
        consumer.subscribe(MqConstant.MQ_TOPIC_01, MqConstant.MQ_TAG_01);
        //设置消息订阅的模式默认是负载均衡模式，可以指定广播模式,总结在MQ中消息消费的模式：负载均衡模式和广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);
        //4、设置消息的回调函数，进行监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            /**
             * 进行消息监听处理消息
             * @param msgs 消息对象
             * @param context 上下文
             * @return 返回状态
             */
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                log.info("msgs:{}", msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5、启动消费者
        consumer.start();
    }
}
