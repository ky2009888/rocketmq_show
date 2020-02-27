package org.ky2009666.apps.mq.prodoucer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.ky2009666.apps.mq.constant.MqConstant;

/**
 * 单项发送消息,不关心结果，例如日志的发送
 *
 * @author Lenovo
 */
@Slf4j
public class SendMsgWithOneWay {
    /**
     * 主入口方法
     *
     * @param args 参数
     */
    public static void main(String[] args) throws Exception {
        //1、创建消息的生产者，并且指定消息组
        DefaultMQProducer producer = new DefaultMQProducer(MqConstant.MQ_PRODUCER_CONSUMER_GROUP_NAME_01);
        //2、指定消息队列服务器的地址
        producer.setNamesrvAddr(MqConstant.MQ_NAME_SERV_ADDR);
        //3、启动生产者
        producer.start();
        //4、创建消息对象
        for (int i = 0; i < 10; i++) {
            Message message = new Message("base","Tag3", ("张三丰来了，请你发标号吧:" + i).getBytes());
            log.info("no:{}",i);
            //5、发送单向消息
            producer.sendOneway(message);
        }
        //消息生产者关闭
        producer.shutdown();
    }
}
