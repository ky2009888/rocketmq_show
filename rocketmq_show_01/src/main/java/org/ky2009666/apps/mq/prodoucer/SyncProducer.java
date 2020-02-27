package org.ky2009666.apps.mq.prodoucer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.ky2009666.apps.mq.constant.MqConstant;

/**
 * @author Lenovo
 * 同步消息队列案例演示
 */
@Slf4j
public class SyncProducer {
    /**
     * 主函数
     *
     * @param args 参数
     */
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //1、创建消息生产者，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer(MqConstant.MQ_PRODUCER_CONSUMER_GROUP_NAME_01);
        //2、指定nameserver地址
        producer.setNamesrvAddr(MqConstant.MQ_NAME_SERV_ADDR);
        //3、启动producer
        producer.start();
        //4、创建消息对象，指定主题Topic，Tag和消息体
        for (int i = 0; i < 10; i++) {
            Message message = new Message("base", "Tag1", ("我是吉曼宁" + i).getBytes());
            //5、发送消息
            SendResult sendResult = producer.send(message);
            log.info(sendResult.toString());
            SendStatus sendStatus = sendResult.getSendStatus();
            String msgId = sendResult.getMsgId();
            log.info("消息ID:{}", msgId);
            log.info("消息状态:{}", sendStatus.toString());
        }
        //6、关闭生产者
        producer.shutdown();
    }
}
