
package com.amazon.hack.amazing.utils;

import com.amazon.hack.amazing.UpstreamServer;
import com.amazon.hack.amazing.model.ItemBean;
import com.amazon.hack.amazing.scheduledtasks.StoreQueue;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Random;

public class FileProcessor {
    private Random random = new Random();
    private Logger logger = Logger.getLogger(FileProcessor.class);

    public boolean process(File file) throws Exception {
        Thread.sleep(random.nextInt(10) * 500);
        logger.info("Processing File: " + file);
        final List<ItemBean> itemBeans = CsvParser.readCSV(file);
            if (itemBeans != null) {
                System.out.println("Sending ------ " + itemBeans);
                UpstreamServer.addToQueue(itemBeans);
        }
        return file.delete();
    }
}