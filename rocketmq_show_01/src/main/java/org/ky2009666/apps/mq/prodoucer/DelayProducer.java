package org.ky2009666.apps.mq.prodoucer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.ky2009666.apps.mq.constant.MqConstant;

/**
 * 延迟消息发送案例
 *
 * @author Lenovo
 */
@Slf4j
public class DelayProducer {
    /**
     * 主入口方法
     *
     * @param args 参数
     */
    public static void main(String[] args) throws Exception {
        //1、定义消息的生产者，并指定消息组
        DefaultMQProducer producer = new DefaultMQProducer(MqConstant.MQ_PRODUCER_CONSUMER_GROUP_NAME_01);
        //2、指定消息的服务器
        producer.setNamesrvAddr(MqConstant.MQ_NAME_SERV_ADDR);
        //3、启动生产者
        producer.start();
        //4、进行消息的主题和Tag设置
        for (int i = 0; i < 5; i++) {
            Message message = new Message(MqConstant.MQ_TOPIC_01, MqConstant.MQ_TAG_01, ("我是延迟消息" + i).getBytes());
            //延迟5秒发送消息,第一个等级的是1s，后面依次类推
            //messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            message.setDelayTimeLevel(2);
            //5、发送消息
            SendResult send = producer.send(message);
            log.info("SendResult:{}", send);
        }
        //6、关闭消息
        producer.shutdown();
    }
}
