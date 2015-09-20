package com.amazon.hack.amazing.amqp;

import com.amazon.hack.amazing.model.ItemBean;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;
import java.util.List;

/**
 * This class implements org.springframework.amqp.core.MessageListener.
 * It is tied to DATA_EXCHANGE and listing to an anonomous queue
 * which picks up message in the  DATA_EXCHANGE with a routing pattern of
 * downstream.routingkey.1  specified in rabbt-listener-contet.xml file.
 */
public class DownstreamDataListener implements MessageListener {
    public DownstreamDataListener() {
        System.out.println("\n\ncreating DownstreamDataListener...\n\n");
    }

    public void onMessage(Message message) {
        List<ItemBean> itemBeans = null;
        try {
            itemBeans = ItemBean.deserialize(message.getBody());
           // System.out.println("\n\n Downstream Request: \n" + itemBeans + "\n\n");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
