package com.amazon.hack.amazing.controller;

import com.amazon.hack.amazing.model.ItemBean;
import com.amazon.hack.amazing.model.MerchantAwareBean;
import com.amazon.hack.amazing.scheduledtasks.StoreQueue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Shaffi on 19-09-2015.
 */
@RestController
public class ItemSchedulerAction  implements ServletContextAware{
    @RequestMapping("/getBatch")
    String schedule(HttpServletRequest request, HttpServletResponse response){//@RequestParam String filterID, @RequestParam String filterValue){
        ServletContext context =request.getSession().getServletContext();
        TreeSet<ItemBean> prioritizedItems = null;
        StoreQueue queue = new StoreQueue();
        int count =0;
        List<ItemBean> upstreamQueue = new ArrayList<ItemBean>();
        prioritizedItems = (TreeSet<ItemBean>) queue.readCSV();
        ItemBean firstItem = prioritizedItems.first();
        MerchantAwareBean merchantAware = new MerchantAwareBean();
        int stopLoading = 10;
        merchantAware = (MerchantAwareBean) context.getAttribute("merchant");

                String merchantId = firstItem.getMerchantID();

        while(load(merchantAware,merchantId)) {
            for (ItemBean item : prioritizedItems) {
                if (count == stopLoading)
                    break;
                if (item.equals(firstItem)) {
                    upstreamQueue.add(item);
                    prioritizedItems.remove(item);
                    count++;
                }
            }
        }
        merchantAware.setMerchantId(merchantId);
        merchantAware.setCurrentTimeStamp(new Timestamp(new Date().getTime()));
        merchantAware.setPayLoad(count);
        context.setAttribute("merchant",merchantAware);

    return null;
    }
    boolean load(MerchantAwareBean merchantAware, String merchantId){
        int stopLoading = 10;
        Timestamp currentTimeStamp = merchantAware.getCurrentTimeStamp();
        boolean flag = true;
        if(merchantAware.getMerchantId().equals(merchantId)){
            long diff =  new Date().getTime() - currentTimeStamp.getTime();
            if((diff/(60*60*1000))>1){
                stopLoading = 10 - merchantAware.getPayLoad();
            }
            else
            flag = false;


        }
    return flag;
    }




    @Override
    public void setServletContext(ServletContext servletContext) {

    }
}
