package com.amazon.hack.amazing.scheduledtasks;

import com.amazon.hack.amazing.UpstreamServer;
import com.amazon.hack.amazing.model.ItemBean;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Scheduler {
    HashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ItemBean>>> priorityMap = null;

    public void schedule(List<ItemBean> itemBeans) {
        List<ItemBean> itemList;
        List<ItemBean> recycleItemList = new ArrayList<ItemBean>();
        List<String> itemIds = new ArrayList<String>();

        ConcurrentHashMap<String, List<ItemBean>> merchantMap;
        HashMap<String, ConcurrentHashMap<String, List<ItemBean>>> priorityMap = new HashMap<String, ConcurrentHashMap<String, List<ItemBean>>>();
        String[] priorities = new String[]{"Highest", "High", "Normal", "Low", "Lowest"};

        BatchScheduler batchScheduler = new BatchScheduler();
        // use comma as separator
        for(ItemBean itemBean:itemBeans) {
            if (priorityMap.containsKey(itemBean.getPriority())) {
                merchantMap = priorityMap.get(itemBean.getPriority());
                if (merchantMap.containsKey(itemBean.getMerchantID())) {
                    itemList = merchantMap.get(itemBean.getMerchantID());
                    if (itemIds.contains(itemBean.getItemID())) {
                        recycleItemList.add(itemBean);
                    } else {
                        itemList.add(itemBean);
                        itemIds.add(itemBean.getItemID());
                    }
                } else {
                    if (itemIds.contains(itemBean.getItemID())) {
                        recycleItemList.add(itemBean);
                    } else {
                        itemList = new ArrayList<ItemBean>();
                        itemList.add(itemBean);
                        itemIds.add(itemBean.getItemID());
                        merchantMap.put(itemBean.getMerchantID(), itemList);
                    }
                }
            } else {
                if (itemIds.contains(itemBean.getItemID())) {
                    recycleItemList.add(itemBean);
                } else {
                    itemList = new ArrayList<ItemBean>();
                    itemList.add(itemBean);
                    itemIds.add(itemBean.getItemID());
                    merchantMap = new ConcurrentHashMap<String, List<ItemBean>>();
                    merchantMap.put(itemBean.getMerchantID(), itemList);
                    priorityMap.put(itemBean.getPriority(), merchantMap);
                }
            }
        }
        //System.out.println("complete map == " + priorityMap);
        batchScheduler.createBatch(priorityMap);
       // System.out.println("Recycle list -- " + recycleItemList);
        if(recycleItemList!=null && !recycleItemList.isEmpty())
            UpstreamServer.addToQueue(recycleItemList);
        recycleItemList = null;
    }
}
