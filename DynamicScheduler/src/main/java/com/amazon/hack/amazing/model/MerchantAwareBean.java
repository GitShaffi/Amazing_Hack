package com.amazon.hack.amazing.model;

import java.sql.Timestamp;
import java.util.Date;

public class MerchantAwareBean {
    String MerchantId;
    Timestamp currentTimeStamp;
    int payLoad;

    public Timestamp getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String merchantId) {
        MerchantId = merchantId;
    }


    public int getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(int payLoad) {
        this.payLoad = payLoad;
    }
}
