package com.amazon.hack.amazing.scheduledtasks;

import com.amazon.hack.amazing.model.ItemBean;

import java.io.*;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class StoreQueue {

    public void batchQueue(File csv){
        Scheduler scheduler = new Scheduler();
        HashMap<String, HashMap<String, HashMap<String,ItemBean>>> priorityMap = scheduler.schedule(csv);


    }


}
