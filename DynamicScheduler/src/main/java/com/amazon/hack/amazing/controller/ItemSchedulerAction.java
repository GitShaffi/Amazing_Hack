package com.amazon.hack.amazing.controller;

import com.amazon.hack.amazing.model.ItemBean;
import com.amazon.hack.amazing.scheduledtasks.Scheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ItemSchedulerAction implements ServletContextAware {
    private static final String TEST_FILE = "merchant_payload.csv";

    @RequestMapping("/getBatch")
    String schedule(HttpServletRequest request, HttpServletResponse response) {//@RequestParam String filterID, @RequestParam String filterValue){
        ServletContext context = request.getSession().getServletContext();
        HashMap<String, HashMap<String, HashMap<String, ItemBean>>> prioritizedItems = null;
        Scheduler queue = new Scheduler();
        int count = 0;
        List<ItemBean> upstreamQueue = new ArrayList<ItemBean>();
        prioritizedItems = queue.schedule(new File(TEST_FILE));
       /* ItemBean firstItem = prioritizedItems.first();
        MerchantAwareBean merchantAware;
        int stopLoading = 10;
        merchantAware = (MerchantAwareBean) context.getAttribute("merchant");

        String merchantId = firstItem.getMerchantID();

        while (!load(merchantAware, merchantId)) {
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
        if(merchantAware == null)
        merchantAware = new MerchantAwareBean();
        merchantAware.setMerchantId(merchantId);
        merchantAware.setCurrentTimeStamp(new Timestamp(new Date().getTime()));
        merchantAware.setPayLoad(count);
        context.setAttribute("merchant", merchantAware);

       */
        return null;
    }

   /* boolean load(MerchantAwareBean merchantAware, String merchantId) {
        int stopLoading = 10;
        boolean flag = true;
        if (merchantAware != null) {
            Timestamp currentTimeStamp = merchantAware.getCurrentTimeStamp();
            if (merchantAware.getMerchantId().equals(merchantId)) {
                long diff = new Date().getTime() - currentTimeStamp.getTime();
                if ((diff / (60 * 60 * 1000)) > 1) {
                    stopLoading = 10 - merchantAware.getPayLoad();
                } else
                    flag = false;


            }
        }
        return flag;
    }
*/

    @Override
    public void setServletContext(ServletContext servletContext) {

    }
}
