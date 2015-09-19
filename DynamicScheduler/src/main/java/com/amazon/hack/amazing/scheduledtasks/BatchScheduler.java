package com.amazon.hack.amazing.scheduledtasks;

import com.amazon.hack.amazing.model.ItemBean;
import com.amazon.hack.amazing.model.VerifyBean;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Shaffi on 19-09-2015.
 */
public class BatchScheduler {
    List<ItemBean> batch = new ArrayList<ItemBean>();
    HashMap<String,HashMap<String,ItemBean>> merchantMap = null;
    HashMap<String,VerifyBean> checkMap = null;
    void createBatch(HashMap<String, HashMap<String, HashMap<String,ItemBean>>> priorityMap){
        int i = 0;
        String[] priorities = new String[]{"Highest","High","Normal","Low","Lowest"};
            while(i<=4){
                merchantMap = priorityMap.get(priorities[i]);
                Iterator it = merchantMap.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry pair = (Map.Entry)it.next();
                    String merchantId = (String)pair.getKey();
                    VerifyBean verify = checkMap.get(merchantId);
                    if(verify != null) {
                        long diff = (new Date().getTime() - verify.getTimestamp().getTime())/(60*60*1000);
                        if (verify.getCount() < 10 && diff < 1) {
                            batch.add((ItemBean) pair.getValue());
                            verify.setCount(verify.getCount() + 1);
                            it.remove();
                        }
                        else if(diff >= 1) {
                            checkMap.remove(merchantId);
                            batch.add((ItemBean) pair.getValue());
                            it.remove();
                        }
                    }
                    else {
                        checkMap.put(merchantId,new VerifyBean(new Timestamp(new Date().getTime()),1));
                        batch.add((ItemBean) pair.getValue());
                        it.remove();
                    }
                }
                System.out.println("The final value nu nenaikaran : " + batch);

            }
    }
}
