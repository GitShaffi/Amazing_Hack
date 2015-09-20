
package com.amazon.hack.amazing.utils;

import com.amazon.hack.amazing.model.ItemBean;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileProcessor {
    private Random random = new Random();
    private Logger logger = Logger.getLogger(FileProcessor.class);
    public static List<ItemBean> itemBeans = new ArrayList<ItemBean>();

    public boolean process(File file) throws Exception {
        Thread.sleep(random.nextInt(10) * 500);
        logger.info("Processing File: " + file);
        final List<ItemBean> itemBean = CsvParser.readCSV(file);
        synchronized(ItemBean.class) {
            itemBeans.addAll(itemBean);
        }
        return file.delete();
    }
}