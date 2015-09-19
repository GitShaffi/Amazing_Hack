package com.amazon.hack.amazing.amqp;

import com.amazon.hack.amazing.model.ItemBean;
import com.amazon.hack.amazing.scheduledtasks.Scheduler;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;
import java.util.List;

/**
 * This class implements org.springframework.amqp.core.MessageListener.
 *  It is tied to DATA_EXCHANGE and listing to an anonomous queue
 *  which picks up message in the  DATA_EXCHANGE with a routing pattern of
 *  my.routingkey.1  specified in rabbt-listener-contet.xml file.
 */
public class DataListener implements MessageListener {
	static Scheduler scheduler = new Scheduler();
public DataListener(){
	System.out.println("\n\ncreating DataListener...\n\n");
}

	public void onMessage(Message message) {
		List<ItemBean> itemBeans= null;
		try {
			itemBeans = ItemBean.deserialize(message.getBody());
			scheduler.schedule(itemBeans);

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
