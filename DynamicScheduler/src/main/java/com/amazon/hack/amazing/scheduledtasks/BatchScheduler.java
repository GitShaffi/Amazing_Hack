package com.amazon.hack.amazing.scheduledtasks;

import com.amazon.hack.amazing.UpstreamServer;
import com.amazon.hack.amazing.model.ItemBean;
import com.amazon.hack.amazing.model.VerifyBean;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BatchScheduler {
    List<ItemBean> batch = new ArrayList<ItemBean>();
    ConcurrentHashMap<String,List<ItemBean>> merchantMap = null;
    List<ItemBean> itemList = new ArrayList();
    HashMap<String,VerifyBean> checkMap = new HashMap<String, VerifyBean>();
    HashMap<String, ConcurrentHashMap<String, List<ItemBean>>> priorityMap;
    List<ItemBean> recycleList = new ArrayList<ItemBean>();
    void createBatch(HashMap<String, ConcurrentHashMap<String, List<ItemBean>>> prioritymap) {
        priorityMap = prioritymap;
        int i = 0;
        String[] priorities = new String[]{"Highest", "High", "Normal", "Low", "Lowest"};
        while (i <= 4) {
            merchantMap = priorityMap.get(priorities[i]);
            //System.out.println(merchantMap.isEmpty()+ " " + i + " ");
            if (merchantMap != null && !merchantMap.isEmpty()) {
                for (String key : merchantMap.keySet()) {

                    List<ItemBean> itemList = merchantMap.get(key);
                    for (ItemBean item : itemList) {
                        //System.out.println("\n pair --- " + pair + "\n");
                        VerifyBean verify = checkMap.get(key);
                        if (verify != null) {
                            long diff = (new Date().getTime() - verify.getTimestamp().getTime()) / (60 * 60 * 1000);
                            if (verify.getCount() < 10) {
                                batch.add(item);
                                removeRecycleData(item);
                                verify.setCount(verify.getCount() + 1);

                            } else if (diff >= 1) {
                                checkMap.remove(key);
                                batch.add(item);
                                removeRecycleData(item);
                                checkMap.put(key, new VerifyBean(new Timestamp(new Date().getTime()), 1));

                            } else {
                                recycleList.add(item);
                                removeRecycleData(item);
                            }
                        } else {
                            checkMap.put(key, new VerifyBean(new Timestamp(new Date().getTime()), 1));
                            removeRecycleData(item);
                            batch.add(item);

                        }
                    }

                    //if(batch!=null)
                    //  System.out.println(batch);
                }
            } else
                i++;
        }
        System.out.println("Final Batch :" + batch);
        UpstreamServer.pushToDownstream(batch);
        if (recycleList != null && !recycleList.isEmpty()) {
            UpstreamServer.addToQueue(recycleList);
            recycleList.clear();
        }
    }


    private void removeRecycleData(ItemBean item) {
        itemList.remove(item);
        if(itemList.size()==0)
            merchantMap.remove(item.getMerchantID());
        if(merchantMap.size() == 0)
            priorityMap.remove(item.getPriority());
    }
}


