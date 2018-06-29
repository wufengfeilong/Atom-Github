package com.fcn.park.manager.bean.car;

import java.util.List;

/**
 * 管理中心的月租车辆待审批功能用Bean
 */
public class CarWaitCheckBean {

    private String totalPage;
    private boolean isNext;
    private List<CarWaitCheckListBean> carWaitCheckList;

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

    public List<CarWaitCheckListBean> getCarWaitCheckList() {
        return carWaitCheckList;
    }

    public void setCarWaitCheckList(List<CarWaitCheckListBean> carWaitCheckList) {
        this.carWaitCheckList = carWaitCheckList;
    }

    public static class CarWaitCheckListBean {

        private String companyName;
        // 车牌号
        private String carNumber;
        // 公司名称
        private String company;
        // 月租车辆id
        private String parkPay_id;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getParkPay_id() {
            return parkPay_id;
        }

        public void setParkPay_id(String parkPay_id) {
            this.parkPay_id = parkPay_id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }
    }

}

