package com.amazon.hack.amazing;

import com.amazon.hack.amazing.model.ItemBean;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * to the DATA-EXCHANGE configured in the rabbt-listener-contet.xml file with the routing key
 * "my.routingkey.1"
 */
public class UpstreamServer {

    private static AmqpTemplate mq;

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("rabbit-sender-context.xml");//loading beans
        mq = (AmqpTemplate) context.getBean("dataTemplate");

//		for (int i = 0; i < 10; i++)
//			mq.convertAndSend("my.routingkey.1", "Message # " + i + " on " + new Date());// send
    }

    public static void addToQueue(ItemBean bean) {
        if (mq == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("rabbit-sender-context.xml");//loading beans
            mq = (AmqpTemplate) context.getBean("dataTemplate");
        }
        mq.convertAndSend("my.routingkey.1", bean);
    }
}
//end of UpstreamServer
