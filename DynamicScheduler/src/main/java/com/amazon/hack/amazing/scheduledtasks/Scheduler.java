package com.amazon.hack.amazing.scheduledtasks;

import com.amazon.hack.amazing.model.ItemBean;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class Scheduler {
    HashMap<String, HashMap<String, HashMap<String, ItemBean>>> priorityMap = null;

    public void schedule(List<ItemBean> itemBeans) {
        ItemBean items = null;
        HashMap<String, ItemBean> itemMap = null;
        HashMap<String, HashMap<String, ItemBean>> merchantMap = null;
        HashMap<String, HashMap<String, HashMap<String, ItemBean>>> priorityMap = null;
        String[] priorities = new String[]{"Highest", "High", "Normal", "Low", "Lowest"};
        BatchScheduler batchScheduler = new BatchScheduler();
        // use comma as separator
        for(ItemBean itemBean:itemBeans) {
            if (priorityMap.containsKey(itemBean.getPriority())) {
                merchantMap = priorityMap.get(itemBean.getPriority());
                if (merchantMap.containsKey(itemBean.getMerchantID())) {
                    itemMap = merchantMap.get(itemBean.getMerchantID());
                    itemMap.put(itemBean.getItemID(), items);
                } else {
                    itemMap = new HashMap<String, ItemBean>();
                    itemMap.put(itemBean.getItemID(), items);
                    merchantMap.put(itemBean.getMerchantID(), itemMap);
                }
            } else {
                for (int j = 0; j <= 4; j++) {
                    if (priorities[j] != itemBean.getPriority())
                        if (priorityMap.containsKey(priorities[j])) {
                            merchantMap = priorityMap.get(itemBean.getPriority());
                            if (merchantMap.containsKey(itemBean.getMerchantID())) {
                                itemMap = merchantMap.get(itemBean.getMerchantID());
                                ItemBean swapItem = itemMap.get(itemBean.getItemID());
                                if (swapItem.getDataType().equals(itemBean.getDataType()) && swapItem.getMerchantID().equals(itemBean.getMerchantID())) {
                                    if (items.getPayLoad() > swapItem.getPayLoad()) {
                                        items.setPayLoad(swapItem.getPayLoad());
                                    }

                                }
                            }
                        }
                }
                merchantMap = new HashMap<String, HashMap<String, ItemBean>>();
                itemMap = new HashMap<String, ItemBean>();
                itemMap.put(itemBean.getItemID(), items);
                merchantMap.put(itemBean.getMerchantID(), itemMap);
                priorityMap.put(itemBean.getPriority(), merchantMap);
            }
        }

        batchScheduler.createBatch(priorityMap);
    }
}
