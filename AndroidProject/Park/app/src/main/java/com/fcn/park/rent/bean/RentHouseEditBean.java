package com.fcn.park.rent.bean;

import java.util.List;

/**
 * Created by 860617024 on 25/04/2018.
 */

public class RentHouseEditBean {

    private RentAddBean rentAddBean;

    private RentInitBean rentInitBean;

    private List<ImageInfo> imageList;

    public RentAddBean getRentAddBean() {
        return rentAddBean;
    }

    public void setRentAddBean(RentAddBean rentAddBean) {
        this.rentAddBean = rentAddBean;
    }

    public RentInitBean getRentInitBean() {
        return rentInitBean;
    }

    public void setRentInitBean(RentInitBean rentInitBean) {
        this.rentInitBean = rentInitBean;
    }

    public List<ImageInfo> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageInfo> imageList) {
        this.imageList = imageList;
    }
}
