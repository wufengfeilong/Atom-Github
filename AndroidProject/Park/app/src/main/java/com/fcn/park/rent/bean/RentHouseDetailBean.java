package com.fcn.park.rent.bean;

import java.util.List;

/**
 * Created by 860617024 on 24/04/2018.
 */

public class RentHouseDetailBean {

    private RentAddBean rentAddBean;

    private List<String> imageList;

    public RentAddBean getRentAddBean() {
        return rentAddBean;
    }

    public void setRentAddBean(RentAddBean rentAddBean) {
        this.rentAddBean = rentAddBean;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
