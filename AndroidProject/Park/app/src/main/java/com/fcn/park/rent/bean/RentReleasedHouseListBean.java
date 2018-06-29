package com.fcn.park.rent.bean;

import java.util.List;

/**
 * Created by 860117066 on 2018/04/25.
 */

public class RentReleasedHouseListBean {
    private String totalPage;

    private boolean isNext;

    private List<RentReleasedHouseListBean.ListReleasedHouseBean> listReleasedHouse;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }

    public List<RentReleasedHouseListBean.ListReleasedHouseBean> getListReleasedHouse() {
        return listReleasedHouse;
    }

    public void setListReleasedHouse(List<RentReleasedHouseListBean.ListReleasedHouseBean> listReleasedHouse) {
        this.listReleasedHouse = listReleasedHouse;
    }

    public static class ListReleasedHouseBean {
        /**

         */

        private int id;
        private String house_area;
        private String house_price;
        private String house_title;
        private String house_address;
        private String house_img;
        private String create_time;

        public String getHouse_img() {
            return house_img;
        }

        public void setHouse_img(String house_img) {
            this.house_img = house_img;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getHouse_address() {
            return house_address;
        }

        public void setHouse_address(String house_address) {
            this.house_address = house_address;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
