package com.amazon.hack.amazing.model;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class ItemBean implements Comparator<ItemBean>,Comparable<ItemBean>, Serializable{
    String itemID;
    String merchantID;
    String marketPlaceID;
    String priority;


    String dataType;
    int payLoad;

    public int getPayLoad() {
        return payLoad;
    }
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


    public boolean equsls(ItemBean item){
        if(this.getDataType().equals(item.getDataType()) && this.getMerchantID().equals(item.getMerchantID()) && this.getMarketPlaceID().equals(item.getMarketPlaceID())&& this.getPriority().equals(item.getPriority()))
            return true;
        return false;
    }

    private int priority(String priority){
        if(priority == "Highest")
            return 1;
        else if(priority == "High")
            return 2;
        else if(priority == "Lowest")
            return 5;
        else if(priority == "Low")
            return 4;
        else
            return 3;
        }

    @Override
    public int compare(ItemBean o1, ItemBean o2) {
        if(priority(o1.getPriority())==priority(o2.getPriority()))
            return 0;
        else if(priority(o1.getPriority())<priority(o2.getPriority()))
            return -1;
        else
            return 1;
    }

    @Override
    public int compareTo(ItemBean o) {
        if(priority(getPriority())==priority(o.getPriority()))
            return 0;
        else if(priority(getPriority())<priority(o.getPriority()))
            return -1;
        else
            return 1;
    }

    public static List<ItemBean> deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (List<ItemBean>) is.readObject();
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "itemID='" + itemID + '\'' +
                ", merchantID='" + merchantID + '\'' +
                ", marketPlaceID='" + marketPlaceID + '\'' +
                ", priority='" + priority + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }

    public void setPayLoad(int payLoad) {
        this.payLoad = payLoad;
    }
}


