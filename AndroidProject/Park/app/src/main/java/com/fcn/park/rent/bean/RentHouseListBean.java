package com.fcn.park.rent.bean;

import java.util.List;

/**
 * 类描述：新闻列表用Bean
 */

public class RentHouseListBean {


    /**
     * getlistNews : [{"newsId":203,"sortKey":1487668265000,"title":"梁杰：今年1-9月中国服务外包增长一枝独秀","category":"专题聚焦","news_thumbnail":"uploadFiles/registerImg/5baa93b6422f4f6b91f8f1069277cc9b.jpg","updateTimeStamp":"2017-02-21","newsSources":"无"},{"newsId":202,"sortKey":1487668186000,"title":"新技术拓宽服务外包领域","category":"专题聚焦","news_thumbnail":"uploadFiles/registerImg/4407596af1094ce18d503f27b8d46068.jpg","updateTimeStamp":"2017-02-21","newsSources":"国际商报"},{"newsId":201,"sortKey":1487668105000,"title":"前10月中企签订服务外包合同额增9.5% 商务部解读","category":"专题聚焦","news_thumbnail":"uploadFiles/registerImg/9cae9e79ae0e416aaeb104a8239bc3cd.jpg","updateTimeStamp":"2017-02-21","newsSources":"中国新闻网"}]
     * totalPage : 1
     * isNext : false
     */

    private String totalPage;

    private boolean isNext;

    private List<ListHouseBean> listHouse;

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

    public List<ListHouseBean> getListHouse() {
        return listHouse;
    }

    public void setListHouse(List<ListHouseBean> listHouse) {
        this.listHouse = listHouse;
    }

    public static class ListHouseBean {
        /**
         * newsId : 203
         * sortKey : 1487668265000
         * title : 梁杰：今年1-9月中国服务外包增长一枝独秀
         * category : 专题聚焦
         * news_thumbnail : uploadFiles/registerImg/5baa93b6422f4f6b91f8f1069277cc9b.jpg
         * updateTimeStamp : 2017-02-21
         * newsSources : 无
         */

        private int id;
        private String house_area;
        private String house_price;
        private String house_title;
        private String house_address;
        private String house_img;

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
    }
}
