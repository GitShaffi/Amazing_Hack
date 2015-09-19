package com.amazon.hack.amazing.scheduledtasks;

import com.amazon.hack.amazing.model.ItemBean;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

public class StoreQueue {

    public Set readCSV(File csvFile) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        ItemBean items = null;
        TreeSet<ItemBean> prioritizedItem = new TreeSet<ItemBean>();
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] item = line.split(cvsSplitBy);
                items = new ItemBean();
                items.setItemID(item[0]);
                items.setMerchantID(item[1]);
                items.setMarketPlaceID(item[2]);
                items.setPriority(item[3]);
                items.setDataType(item[4]);
                prioritizedItem.add(items);

            }
            return prioritizedItem;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
