package org.ky2009666.apps.mq.prodoucer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.ky2009666.apps.mq.constant.MqConstant;

import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息
 *
 * @author Lenovo
 */
@Slf4j
public class AsyncProducer {
    /**
     * 定义集群的地址
     */
    private static final String NAME_SRV_ADDR = "192.168.64.7:9876;192.168.64.8:9876";

    /**
     * 主入口方法
     *
     * @param args 参数
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //1、创建消息生产者，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer(MqConstant.MQ_PRODUCER_CONSUMER_GROUP_NAME_01);
        //2、指定nameser的地址
        producer.setNamesrvAddr(MqConstant.MQ_NAME_SERV_ADDR);
        producer.setVipChannelEnabled(false);
        //3、启动producer
        producer.start();
        producer.setRetryTimesWhenSendFailed(0);
        //4、创建消息对象
        for (int i = 0; i < 10; i++) {
            Message message = new Message("base", "Tag2", ("我是吉金梁,当前编码是:" + i).getBytes());
            //5、异步发送消息
            producer.send(message, new SendCallback() {
                /**
                 * 发送成功的回调函数
                 *
                 * @param sendResult 发送结果
                 */
                public void onSuccess(SendResult sendResult) {
                    log.info("发送结果:{}", sendResult);
                }

                /**
                 * 发送失败的回调函数
                 *
                 * @param throwable 异常信息
                 */
                public void onException(Throwable throwable) {
                    log.error("发送异常:{}", throwable.getMessage());
                }
            });
            //发送异步消模拟逻辑处理
            TimeUnit.MILLISECONDS.sleep(100);
            //System.out.println("message = " + message);
        }
        //6、关闭生产者
        producer.shutdown();
    }
}
