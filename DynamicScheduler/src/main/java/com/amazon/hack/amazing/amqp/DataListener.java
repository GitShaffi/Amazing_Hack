package com.amazon.hack.amazing.amqp;

import com.amazon.hack.amazing.model.ItemBean;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;

/**
 * This class implements org.springframework.amqp.core.MessageListener.
 *  It is tied to DATA_EXCHANGE and listing to an anonomous queue
 *  which picks up message in the  DATA_EXCHANGE with a routing pattern of
 *  my.routingkey.1  specified in rabbt-listener-contet.xml file.
 */
public class DataListener implements MessageListener {
public DataListener(){
	System.out.println("\n\ncreating DataListener...\n\n");
}
	public void onMessage(Message message) {
		ItemBean itemBean= null;
		try {
			itemBean = ItemBean.deserialize(message.getBody());
			System.out.println("\n\nListener received message----->"+itemBean.getItemID()+"\n\n");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
//end of DataListener.
/* You can imagine the possibilities of using this listener to process jobs asynchronously. You can invoke a Spring Batch jobs if you want.
 * 
 * 
 * */
