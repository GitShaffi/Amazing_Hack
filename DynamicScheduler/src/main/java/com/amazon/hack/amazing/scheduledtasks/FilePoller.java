package com.amazon.hack.amazing.scheduledtasks;

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
            out.write("ITEM1,12345,4,LOW,ITEM\n");
            out.write("ITEM2,12645,1,HIGH,PRICE\n");
            out.write("ITEM3,17345,4,LOW,AUX_ITEM\n");
            out.write("ITEM2,12645,5,LOWEST,ITEM\n");
            out.write("ITEM3,19345,4,HIGHEST,AUX_ITEM\n");
            out.write("ITEM1,12345,4,NORMAL,ITEM\n");
            out.write("ITEM1,12345,4,LOW,ITEM");
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
}