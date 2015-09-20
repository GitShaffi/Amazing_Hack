package com.amazon.hack.amazing;

import com.amazon.hack.amazing.model.ItemBean;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * to the DATA-EXCHANGE configured in the rabbit-listener-contet.xml file with the routing key
 * "my.routingkey.1"
 */
public class UpstreamServer {

    private static AmqpTemplate itemBeanMq;
    private static AmqpTemplate DownstreamMq;

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("rabbit-sender-context.xml");//loading beans
        itemBeanMq = (AmqpTemplate) context.getBean("dataTemplate");

//		for (int i = 0; i < 10; i++)
//			itemBeanMq.convertAndSend("my.routingkey.1", "Message # " + i + " on " + new Date());// send
    }

    public static void addToQueue(List<ItemBean> bean) {
        if (itemBeanMq == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("rabbit-sender-context.xml");//loading beans
            itemBeanMq = (AmqpTemplate) context.getBean("dataTemplate");
        }
        itemBeanMq.convertAndSend("my.routingkey.1", bean);
    }

    public static void pushToDownstream(List<ItemBean> bean) {
        if (DownstreamMq == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("rabbit-sender-context.xml");//loading beans
            DownstreamMq = (AmqpTemplate) context.getBean("dataTemplate");
        }
        DownstreamMq.convertAndSend("downstream.routingkey.1", bean);
    }
}
//end of UpstreamServer
