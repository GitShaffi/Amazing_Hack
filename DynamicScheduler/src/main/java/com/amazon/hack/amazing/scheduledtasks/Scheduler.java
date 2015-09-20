package com.amazon.hack.amazing.scheduledtasks;

import com.amazon.hack.amazing.UpstreamServer;
import com.amazon.hack.amazing.model.ItemBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Scheduler {
    HashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ItemBean>>> priorityMap = null;
    ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, List<ItemBean>>>>> recycleMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, List<ItemBean>>>>>();
    public void schedule(List<ItemBean> itemBeans) {
        List<ItemBean> itemList;
        List<ItemBean> recycleItemList = new ArrayList<ItemBean>();
        List<String> itemIds = new ArrayList<String>();

        ConcurrentHashMap<String, List<ItemBean>> merchantMap;

        HashMap<String, ConcurrentHashMap<String, List<ItemBean>>> priorityMap = new HashMap<String, ConcurrentHashMap<String, List<ItemBean>>>();
        String[] priorities = new String[]{"Highest", "High", "Normal", "Low", "Lowest"};

        BatchScheduler batchScheduler = new BatchScheduler();
        // use comma as separator
        for (final ItemBean itemBean : itemBeans) {
            if (priorityMap.containsKey(itemBean.getPriority())) {
                merchantMap = priorityMap.get(itemBean.getPriority());
                if (merchantMap.containsKey(itemBean.getMerchantID())) {
                    itemList = merchantMap.get(itemBean.getMerchantID());
                    if (itemIds.contains(itemBean.getItemID())) {
                        recycleItemList.add(itemBean);
                        getRecycleHashMap(itemBean);
                    } else {
                        itemList.add(itemBean);
                        itemIds.add(itemBean.getItemID());
                    }
                } else {
                    if (itemIds.contains(itemBean.getItemID())) {
                        recycleItemList.add(itemBean);
                        getRecycleHashMap(itemBean);
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
                    getRecycleHashMap(itemBean);
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
        batchScheduler.createBatch(priorityMap,recycleMap);
       // System.out.println("Recycle list -- " + recycleItemList);
        if(recycleItemList!=null && !recycleItemList.isEmpty())
            UpstreamServer.addToQueue(recycleItemList);
        recycleItemList = null;
    }
    void  getRecycleHashMap(ItemBean itemBean) {
        List<ItemBean> itemBeanList = new ArrayList<ItemBean>();
        ConcurrentHashMap<String, ConcurrentHashMap<String, List<ItemBean>>> recycleMerchantMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, List<ItemBean>>>();
        ConcurrentHashMap<String, List<ItemBean>> recyclePriorityMap = new ConcurrentHashMap<String, List<ItemBean>>();
        ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, List<ItemBean>>>> recycleMarketPlaceMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String, List<ItemBean>>>>();
        if (recycleMap.contains(itemBean.getItemID())) {
            recycleMarketPlaceMap = recycleMap.get(itemBean.getItemID());
            if (recycleMarketPlaceMap.containsKey(itemBean.getMerchantID())) {
                recycleMerchantMap = recycleMarketPlaceMap.get(itemBean.getMerchantID());
                if (recycleMerchantMap.containsKey(itemBean.getMarketPlaceID())) {
                    recyclePriorityMap = recycleMerchantMap.get(itemBean.getMarketPlaceID());
                    if (recyclePriorityMap.containsKey(itemBean.getPriority())) {
                        itemBeanList = recyclePriorityMap.get(itemBean.getPriority());
                        itemBeanList.add(itemBean);
                    } else {
                        itemBeanList = new ArrayList<ItemBean>();
                        itemBeanList.add(itemBean);
                        recyclePriorityMap.put(itemBean.getPriority(), itemBeanList);
                    }
                } else {
                    itemBeanList = new ArrayList<ItemBean>();
                    itemBeanList.add(itemBean);
                    recyclePriorityMap.put(itemBean.getPriority(), itemBeanList);
                    recycleMerchantMap.put(itemBean.getMerchantID(), recyclePriorityMap);
                }


            } else {
                itemBeanList = new ArrayList<ItemBean>();
                itemBeanList.add(itemBean);
                recyclePriorityMap.put(itemBean.getPriority(), itemBeanList);
                recycleMerchantMap.put(itemBean.getMerchantID(), recyclePriorityMap);
                recycleMarketPlaceMap.put(itemBean.getMarketPlaceID(), recycleMerchantMap);
            }
        } else {
            itemBeanList = new ArrayList<ItemBean>();
            itemBeanList.add(itemBean);
            recyclePriorityMap.put(itemBean.getPriority(), itemBeanList);
            recycleMerchantMap.put(itemBean.getMerchantID(), recyclePriorityMap);
            recycleMarketPlaceMap.put(itemBean.getMarketPlaceID(), recycleMerchantMap);
            recycleMap.put(itemBean.getItemID(), recycleMarketPlaceMap);
        }
    }
}
