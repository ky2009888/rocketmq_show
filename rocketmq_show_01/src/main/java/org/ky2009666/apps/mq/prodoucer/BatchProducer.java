package org.ky2009666.apps.mq.prodoucer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.ky2009666.apps.mq.constant.MqConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息生产者批量发送消息
 *
 * @author Lenovo
 */
@Slf4j
public class BatchProducer {
    /**
     * 主入口方法
     *
     * @param args 参数
     */
    public static void main(String[] args) throws Exception {
        //1、定义消息的生产者，并指定组名
        DefaultMQProducer producer = new DefaultMQProducer(MqConstant.MQ_PRODUCER_CONSUMER_GROUP_NAME_01);
        //2、指定消息队列的服务器地址
        producer.setNamesrvAddr(MqConstant.MQ_NAME_SERV_ADDR);
        //3、启动消息生产者
        producer.start();
        //4、设置消息的主题和TAG
        List<Message> messageList = new ArrayList<Message>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message(MqConstant.MQ_TOPIC_01, MqConstant.MQ_TAG_01, ("我们是批量消息" + i).getBytes());
            messageList.add(message);
        }
        SendResult send = producer.send(messageList);
        log.info("SendResult：{}", send);
        producer.shutdown();
    }
}
