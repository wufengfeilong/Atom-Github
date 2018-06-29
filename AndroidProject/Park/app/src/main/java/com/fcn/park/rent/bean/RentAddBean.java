package com.fcn.park.rent.bean;

import java.util.List;

/**
 * Created by 860617024 on 23/04/2018.
 */

public class RentAddBean {

    private String houseId;

    private String house_area;

    private String house_price;

    private String house_title;

    private String house_type;

    private String house_address;

    private String house_direct;

    private String house_img;

    private String house_contact;

    private String house_phone;

    private String house_status;

    private String create_user;

    private List<ImageInfo> imageList;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public List<ImageInfo> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageInfo> imageList) {
        this.imageList = imageList;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getHouse_status() {
        return house_status;
    }

    public void setHouse_status(String house_status) {
        this.house_status = house_status;
    }

    public String getHouse_area() {
        return house_area;
    }

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public String getHouse_price() {
        return house_price;
    }

    public void setHouse_price(String house_price) {
        this.house_price = house_price;
    }

    public String getHouse_title() {
        return house_title;
    }

    public void setHouse_title(String house_title) {
        this.house_title = house_title;
    }

    public String getHouse_type() {
        return house_type;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public String getHouse_address() {
        return house_address;
    }

    public void setHouse_address(String house_address) {
        this.house_address = house_address;
    }

    public String getHouse_direct() {
        return house_direct;
    }

    public void setHouse_direct(String house_direct) {
        this.house_direct = house_direct;
    }

    public String getHouse_img() {
        return house_img;
    }

    public void setHouse_img(String house_img) {
        this.house_img = house_img;
    }

    public String getHouse_contact() {
        return house_contact;
    }

    public void setHouse_contact(String house_contact) {
        this.house_contact = house_contact;
    }

    public String getHouse_phone() {
        return house_phone;
    }

    public void setHouse_phone(String house_phone) {
        this.house_phone = house_phone;
    }
}
