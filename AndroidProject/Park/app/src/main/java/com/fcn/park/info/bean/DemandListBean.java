package com.fcn.park.info.bean;

import java.util.List;

/**
 * Created by liuyq on 2018/04/19.
 * 需求列表园区动态使用
 */

public class DemandListBean {
    private String totalPage;
    private boolean isNext;
    private List<DemandsListBean> demandlist;

    public String getTotalPage() {
        return totalPage;
    }

    public boolean isIsNext() {
        return isNext;
    }

    public void setIsNext(boolean isNext) {
        this.isNext = isNext;
    }

    public List<DemandsListBean> getDemandlist() {
        return demandlist;
    }

    public void setDemandlist(List<DemandsListBean> demandlist) {
        this.demandlist = demandlist;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }


    public static class DemandsListBean {

        private String demandId;
        private String title;
        private int category;
        private String contact;
        private String tel;
        private String source;

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getDemandId() {
            return demandId;
        }

        public void setDemandId(String demandId) {
            this.demandId = demandId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

    }

}
