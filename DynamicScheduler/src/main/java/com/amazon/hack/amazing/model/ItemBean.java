package com.amazon.hack.amazing.model;

/**
 * Created by Shaffi on 19-09-2015.
 */
public class ItemBean {
    String itemID;
    String merchantID;
    String marketPlaceID;
    String priority;
    String dataType;

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getMarketPlaceID() {
        return marketPlaceID;
    }

    public void setMarketPlaceID(String marketPlaceID) {
        this.marketPlaceID = marketPlaceID;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
