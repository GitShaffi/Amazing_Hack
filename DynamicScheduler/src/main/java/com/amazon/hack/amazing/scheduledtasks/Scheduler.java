package com.amazon.hack.amazing.scheduledtasks;

import com.amazon.hack.amazing.model.ItemBean;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Scheduler {
    HashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ItemBean>>> priorityMap = null;

    public void schedule(List<ItemBean> itemBeans) {
        ConcurrentHashMap<String, ItemBean> itemMap;
        ConcurrentHashMap<String, ConcurrentHashMap<String, ItemBean>> merchantMap;
        HashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ItemBean>>> priorityMap = new HashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ItemBean>>>();
        String[] priorities = new String[]{"Highest", "High", "Normal", "Low", "Lowest"};
        BatchScheduler batchScheduler = new BatchScheduler();
        // use comma as separator
        for(ItemBean itemBean:itemBeans) {
            if (priorityMap.containsKey(itemBean.getPriority())) {
                merchantMap = priorityMap.get(itemBean.getPriority());
                if (merchantMap.containsKey(itemBean.getMerchantID())) {
                    itemMap = merchantMap.get(itemBean.getMerchantID());
                    itemMap.put(itemBean.getItemID(), itemBean);
                } else {
                    itemMap = new ConcurrentHashMap<String, ItemBean>();
                    itemMap.put(itemBean.getItemID(), itemBean);
                    merchantMap.put(itemBean.getMerchantID(), itemMap);
                }
            } else {
                for (int j = 0; j <= 4; j++) {
                    if (!priorities[j].equals( itemBean.getPriority()))
                        if (priorityMap.containsKey(priorities[j])) {
                            merchantMap = priorityMap.get(itemBean.getPriority());
                            if(merchantMap!=null) {
                                if (merchantMap.containsKey(itemBean.getMerchantID())) {
                                    itemMap = merchantMap.get(itemBean.getMerchantID());
                                    ItemBean swapItem = itemMap.get(itemBean.getItemID());
                                    if (swapItem.getDataType().equals(itemBean.getDataType()) && swapItem.getMerchantID().equals(itemBean.getMerchantID())) {
                                        if (itemBean.getPayLoad() > swapItem.getPayLoad()) {
                                            itemBean.setPayLoad(swapItem.getPayLoad());
                                        }

                                    }
                                }
                            }
                        }
                }
                merchantMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, ItemBean>>();
                itemMap = new ConcurrentHashMap<String, ItemBean>();
                itemMap.put(itemBean.getItemID(), itemBean);
                merchantMap.put(itemBean.getMerchantID(), itemMap);
                priorityMap.put(itemBean.getPriority(), merchantMap);
            }
        }
        System.out.println("complete map == " + priorityMap);
        batchScheduler.createBatch(priorityMap);
    }
}
