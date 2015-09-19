package com.amazon.hack.amazing.scheduledtasks;

import com.amazon.hack.amazing.model.ItemBean;

import java.io.*;
import java.util.HashMap;

public class Scheduler {
    HashMap<String, HashMap<String, HashMap<String,ItemBean>>> priorityMap = null;
    public HashMap<String, HashMap<String, HashMap<String,ItemBean>>> schedule(File csvFile){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        ItemBean items = null;
        HashMap<String,ItemBean> itemMap = null;
        HashMap<String, HashMap<String,ItemBean>> merchantMap = null;
        HashMap<String, HashMap<String, HashMap<String,ItemBean>>> priorityMap = null;
        String[] priorities = new String[]{"Highest","High","Normal","Low","Lowest"};
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
                items.setPayLoad(Integer.parseInt(item[5]));
                if(priorityMap.containsKey(item[3])){
                    merchantMap = priorityMap.get(item[3]);
                    if(merchantMap.containsKey(item[1])){
                        itemMap = merchantMap.get(item[1]);
                        itemMap.put(item[0],items);
                    }
                    else{
                        itemMap = new HashMap<String,ItemBean>();
                        itemMap.put(item[0],items);
                        merchantMap.put(item[1],itemMap);
                    }
                }
                else{
                    for(int j=0;j<=4;j++) {
                        if (priorities[j] != item[3]) if (priorityMap.containsKey(item[3])) {
                            merchantMap = priorityMap.get(item[3]);
                            if (merchantMap.containsKey(item[1])) {
                                itemMap = merchantMap.get(item[1]);
                                ItemBean swapItem = itemMap.get(item[0]);
                                if (swapItem.getDataType().equals(item[4]) && swapItem.getMerchantID().equals(item[2])) {
                                    if (items.getPayLoad() > swapItem.getPayLoad()) {
                                        items.setPayLoad(swapItem.getPayLoad());
                                    }

                                }
                            }
                        }
                    }
                    merchantMap = new HashMap<String, HashMap<String, ItemBean>>();
                    itemMap = new HashMap<String, ItemBean>();
                    itemMap.put(item[0],items);
                    merchantMap.put(item[1],itemMap);
                    priorityMap.put(item[3],merchantMap);
                }

            }
            return priorityMap;

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
