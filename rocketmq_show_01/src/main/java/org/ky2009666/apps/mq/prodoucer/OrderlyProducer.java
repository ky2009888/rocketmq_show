package org.ky2009666.apps.mq.prodoucer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.ky2009666.apps.mq.constant.MqConstant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 顺序发送消息的案例
 *
 * @author Lenovo
 */
public class OrderlyProducer {
    /**
     * 定义订单列表
     */
    private List<Order> orderList;

    /**
     * 构造方法
     */
    public OrderlyProducer() {
        Order order1 = new Order(Long.parseLong("1100"), "订单创建");
        Order order2 = new Order(Long.parseLong("1100"), "订单支付");
        Order order3 = new Order(Long.parseLong("1100"), "订单消息同步");
        Order order4 = new Order(Long.parseLong("1100"), "订单完成");
        Order order5 = new Order(Long.parseLong("1101"), "订单创建");
        Order order6 = new Order(Long.parseLong("1101"), "订单支付");
        Order order7 = new Order(Long.parseLong("1101"), "订单消息同步");
        Order order8 = new Order(Long.parseLong("1101"), "订单完成");
        Order order9 = new Order(Long.parseLong("1102"), "订单创建");
        Order order10 = new Order(Long.parseLong("1102"), "订单支付");
        Order order11 = new Order(Long.parseLong("1102"), "订单消息同步");
        Order order12 = new Order(Long.parseLong("1102"), "订单完成");
        orderList = new ArrayList<Order>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        orderList.add(order4);
        orderList.add(order5);
        orderList.add(order6);
        orderList.add(order7);
        orderList.add(order8);
        orderList.add(order9);
        orderList.add(order10);
        orderList.add(order11);
        orderList.add(order12);
    }

    /**
     * 主入口函数
     *
     * @param args 参数
     */
    public static void main(String[] args) throws Exception {
        //1、创建消息的生产者，并指定组名
        DefaultMQProducer producer = new DefaultMQProducer(MqConstant.MQ_PRODUCER_CONSUMER_GROUP_NAME_01);
        //2、指定消息队列的服务器地址
        producer.setNamesrvAddr(MqConstant.MQ_NAME_SERV_ADDR);
        //3、启动生产者
        producer.start();
        OrderlyProducer orderlyProducer = new OrderlyProducer();
        //4、设置消息
        for (int i = 0; i < orderlyProducer.orderList.size(); i++) {
            Order order = orderlyProducer.orderList.get(i);
            String orderBody = order.toString();
            Message message = new Message(MqConstant.MQ_TOPIC_01, MqConstant.MQ_TAG_01, i + "", orderBody.getBytes());
            //5、发送消息
            producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    long orderId = (Long) arg;
                    long index = orderId % mqs.size();
                    return mqs.get((int)index);
                }
            }, order.getOrderId());
        }
        //6、关闭消息生产者
        producer.shutdown();
    }

    /**
     * 定义订单内部类
     */
    @Data
    @AllArgsConstructor
    @ToString
    private class Order implements Serializable {
        private long orderId;
        private String orderDesc;
    }
}
