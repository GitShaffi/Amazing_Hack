package com.amazon.hack.amazing.controller;

import com.amazon.hack.amazing.model.ItemBean;
import com.amazon.hack.amazing.scheduledtasks.StoreQueue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Shaffi on 19-09-2015.
 */
@RestController
public class ItemSchedulerAction {
    @RequestMapping("/getBatch")
    void schedule(@RequestParam String filterID, @RequestParam String filterValue){
        HashMap<String,List<ItemBean>> itemQueue = null;
        StoreQueue queue = new StoreQueue();
        itemQueue = queue.readCSV();
        List<ItemBean> itemList = null;
        itemList = itemQueue.get(filterValue);
         
    }
}
