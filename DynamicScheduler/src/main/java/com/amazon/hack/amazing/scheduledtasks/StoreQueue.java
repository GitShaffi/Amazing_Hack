package com.amazon.hack.amazing.scheduledtasks;

/**
 * Created by Shaffi on 19-09-2015.
 */
import com.amazon.hack.amazing.model.ItemBean;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class StoreQueue {

    public Set readCSV(){
        String csvFile = "/Users/mkyong/Downloads/GeoIPCountryWhois.csv";
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
