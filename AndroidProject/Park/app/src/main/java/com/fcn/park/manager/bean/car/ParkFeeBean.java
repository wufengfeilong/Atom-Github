package com.fcn.park.manager.bean.car;

import java.util.List;

/**
 * 管理中心的停车付费列表功能用Bean
 */
public class ParkFeeBean {

    private String totalPage;
    private boolean isNext;
    private List<ParkFeeListBean> parkFeeList;

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

    public List<ParkFeeListBean> getParkFeeList() {
        return parkFeeList;
    }

    public void setParkFeeList(List<ParkFeeListBean> parkFeeList) {
        this.parkFeeList = parkFeeList;
    }

    public static class ParkFeeListBean {



        private int delFlg;
        private String companyName;
        // 车牌号
        private String carNumber;
        // 公司名称
        private String paymenttime;
        // 车辆id
        private String parkPay_id;

        public int getDelFlg() {
            return delFlg;
        }

        public void setDelFlg(int delFlg) {
            this.delFlg = delFlg;
        }



        public String getPaymenttime() {
            return paymenttime;
        }

        public void setPaymenttime(String paymenttime) {
            this.paymenttime = paymenttime;
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

