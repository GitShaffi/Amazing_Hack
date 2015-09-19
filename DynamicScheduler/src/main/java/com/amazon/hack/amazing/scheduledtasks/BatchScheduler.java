package com.amazon.hack.amazing.scheduledtasks;

import com.amazon.hack.amazing.model.ItemBean;
import com.amazon.hack.amazing.model.VerifyBean;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BatchScheduler {
    List<ItemBean> batch = new ArrayList<ItemBean>();
    ConcurrentHashMap<String,ConcurrentHashMap<String,ItemBean>> merchantMap = null;
    ConcurrentHashMap<String,ItemBean> itemMap = new ConcurrentHashMap<String, ItemBean>();
    HashMap<String,VerifyBean> checkMap = new HashMap<String, VerifyBean>();
    HashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String,ItemBean>>> priorityMap;
    void createBatch(HashMap<String, ConcurrentHashMap<String, ConcurrentHashMap<String,ItemBean>>> prioritymap){
        priorityMap = prioritymap;
        int i = 0;
        String[] priorities = new String[]{"Highest","High","Normal","Low","Lowest"};
            while(i<=4) {
                merchantMap = priorityMap.get(priorities[i]);
                //System.out.println(merchantMap.isEmpty()+ " " + i + " ");
                if (merchantMap != null && !merchantMap.isEmpty()) {
                    for(String key:merchantMap.keySet()) {

                        //System.out.println("\n pair --- " + pair + "\n");
                        VerifyBean verify = checkMap.get(key);
                        if (verify != null) {
                            long diff = (new Date().getTime() - verify.getTimestamp().getTime()) / (60 * 60 * 1000);
                            if (verify.getCount() < 10) {

                                itemMap =(ConcurrentHashMap) merchantMap.get(key);
                                addItem(itemMap,batch);
                                verify.setCount(verify.getCount() + 1);

                            } else if (diff >= 1) {
                                checkMap.remove(key);
                                itemMap =(ConcurrentHashMap) merchantMap.get(key);
                                addItem(itemMap, batch);
                                checkMap.put(key, new VerifyBean(new Timestamp(new Date().getTime()), 1));

                            }
                        } else {
                            checkMap.put(key, new VerifyBean(new Timestamp(new Date().getTime()), 1));
                            itemMap =(ConcurrentHashMap) merchantMap.get(key);
                            addItem(itemMap, batch);

                        }
                    }
                    //if(batch!=null)
                      //  System.out.println(batch);
                }
                else
                    i++;
            }
    }

    private void addItem(ConcurrentHashMap<String, ItemBean> itemMap, List<ItemBean> batch) {
        for(String key:itemMap.keySet()) {
            final ItemBean value = (ItemBean) itemMap.get(key);
            System.out.println("\n" +value.getItemID() + "\n" + " " + itemMap.size() + " " + merchantMap.size() + " " + merchantMap);
            batch.add(value);
            System.out.println(batch);
            itemMap.remove(key);
            System.out.println(itemMap.size() + " " + merchantMap.size());
            if(itemMap.size() == 0)
                merchantMap.remove(value.getMerchantID());
            if(merchantMap.size() == 0)
                priorityMap.remove(value.getPriority());
        }
    }


}
