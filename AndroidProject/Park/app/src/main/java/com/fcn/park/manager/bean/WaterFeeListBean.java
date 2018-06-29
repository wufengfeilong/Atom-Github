package com.fcn.park.manager.bean;

import java.util.List;

/**
 * 水费列表用Bean.
 */

public class WaterFeeListBean {

    private String totalPage;

    private boolean isNext;

    private List<WaterFeeListBean.ListWaterBean> listWater;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<WaterFeeListBean.ListWaterBean> getListWater() {
        return listWater;
    }

    public void setListWater(List<WaterFeeListBean.ListWaterBean> listWater) {
        this.listWater = listWater;
    }

    public boolean isIsNext() {
        return isNext;
    }

    public void setIsNext(boolean isNext) {
        this.isNext = isNext;
    }

    public static class ListWaterBean {
        private String waterFeeId;

        private String userId;

        private String companyName;

        private String companyMail;

        private String startNum;

        private String endNum;

        private String costNum;

        private String unitPrice;

        private String recordDate;

        private String fee;

        private Integer isPay;

        public String getWaterFeeId() {
            return waterFeeId;
        }

        public void setWaterFeeId(String waterFeeId) {
            this.waterFeeId = waterFeeId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId == null ? null : userId.trim();
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName == null ? null : companyName.trim();
        }

        public String getCompanyMail() {
            return companyMail;
        }

        public void setCompanyMail(String companyMail) {
            this.companyMail = companyMail == null ? null : companyMail.trim();
        }

        public String getStartNum() {
            return startNum;
        }

        public void setStartNum(String startNum) {
            this.startNum = startNum == null ? null : startNum.trim();
        }

        public String getEndNum() {
            return endNum;
        }

        public void setEndNum(String endNum) {
            this.endNum = endNum == null ? null : endNum.trim();
        }

        public String getCostNum() {
            return costNum;
        }

        public void setCostNum(String costNum) {
            this.costNum = costNum == null ? null : costNum.trim();
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitprice) {
            this.unitPrice = unitprice == null ? null : unitprice.trim();
        }

        public String getRecordDate() {
            return recordDate;
        }

        public void setRecordDate(String recordDate) {
            this.recordDate = recordDate == null ? null : recordDate.trim();
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee == null ? null : fee.trim();
        }

        public Integer getIsPay() {
            return isPay;
        }

        public void setIsPay(Integer isPay) {
            this.isPay = isPay;
        }
    }
}
