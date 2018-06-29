package com.fcn.park.property.bean;

import java.util.List;

/**
 * Created by 860117073 on 2018/4/16.
 */

public  class PropertyDeliverWaterBean {

    private List<DeliverWaterBean> getDeliverWater;

    private boolean isNext;

    public List<DeliverWaterBean> getGetDeliverWater() {
        return getDeliverWater;
    }

    public void setGetDeliverWater(List<DeliverWaterBean> getDeliverWater) {
        this.getDeliverWater = getDeliverWater;
    }

    public boolean isIsNext() {
        return isNext;
    }

    public void setIsNext(boolean isNext) {
        this.isNext = isNext;
    }

    public static class DeliverWaterBean{

    private String businessName; //商家名称
    private String synopsis; //简介
    private String phone; //电话号码
    private String address; //地址
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    }
}
