package com.amazon.hack.amazing.scheduledtasks;

import com.amazon.hack.amazing.UpstreamServer;
import com.amazon.hack.amazing.utils.FileProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.PollableChannel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

@Component
public class FilePoller {
    private Logger logger = Logger.getLogger(FilePoller.class.getSimpleName());
    private PollableChannel filesOutChannel;

    @Scheduled(fixedRate = 60000)
    public void createFile() throws IOException, InterruptedException {
        logger.info("\n\n#### Starting Sequential processing test ####");
        logger.info("Populating directory with files");
        File directory = new File("input");
        if (directory.exists()) {
            directory.delete();
        }
        directory.mkdir();
        for (int i = 0; i < 5; i++) {
            File file = new File("input/file_" + i + ".txt");
            logger.info(file.getAbsolutePath());
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write("ITEM1,12345,4,Low,ITEM\n");
            out.write("ITEM2,12345,1,High,PRICE\n");
            out.write("ITEM2,12345,4,Low,AUX_ITEM\n");
            out.write("ITEM4,12345,5,Lowest,ITEM\n");
            out.write("ITEM2,12345,5,Lowest,ITEM\n");
            out.write("ITEM3,12345,4,Highest,AUX_ITEM\n");
            out.write("ITEM6,12345,4,Highest,AUX_ITEM\n");
            out.write("ITEM3,12345,4,Highest,AUX_ITEM\n");
            out.write("ITEM1,12345,4,Normal,ITEM\n");
            out.write("ITEM3,12345,4,Normal,ITEM\n");
            out.write("ITEM1,12345,4,Normal,ITEM\n");
            out.write("ITEM2,12345,4,Low,ITEM");
            out.close();
        }
        logger.info("Populated directory with files");
        logger.info("Starting Spring Integration Sequential File processing");
        ConfigurableApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/spring/integration/sequentialFileProcessing-config.xml");
        filesOutChannel = ac.getBean("filesOutChannel", PollableChannel.class);
        // ac.stop();
    }

    @Scheduled(fixedRate = 5000)
    public void pollForFile() {
        try {
            if (filesOutChannel != null) {
                logger.info("Finished processing " + filesOutChannel.receive(10000).getPayload());
            }
        } catch (NullPointerException ignored) {

        }
    }

    @Scheduled(fixedRate = 5000)
    public void pushItemsToQueue() {
        if(FileProcessor.itemBeans != null && !FileProcessor.itemBeans.isEmpty()) {
            //System.out.println("Sending ------ " + FileProcessor.itemBeans);
            UpstreamServer.addToQueue(FileProcessor.itemBeans);
            FileProcessor.itemBeans.clear();
        }
    }
}
