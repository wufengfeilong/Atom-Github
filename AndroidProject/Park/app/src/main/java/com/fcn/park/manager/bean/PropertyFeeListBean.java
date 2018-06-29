package com.fcn.park.manager.bean;

import java.util.List;

/**
 * 物业费列表用Bean.
 */

public class PropertyFeeListBean {

    private String totalPage;

    private boolean isNext;

    private List<PropertyFeeListBean.ListPropertyBean> listProperty;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<PropertyFeeListBean.ListPropertyBean> getListProperty() {
        return listProperty;
    }

    public void setListProperty(List<PropertyFeeListBean.ListPropertyBean> listPropety) {
        this.listProperty = listPropety;
    }

    public boolean isIsNext() {
        return isNext;
    }

    public void setIsNext(boolean isNext) {
        this.isNext = isNext;
    }

    public static class ListPropertyBean {

        private String propertyFeeId;

        private String companyName;

        private String companyMail;

        private String startDate;

        private String endDate;

        private String companySpace;

        private String unitPrice;

        private String discount;

        private String fee;

        private String comment;

        private Integer isPay;

        private Integer delFlag;

        public String getPropertyFeeId() {
            return propertyFeeId;
        }

        public void setPropertyFeeId(String propertyFeeId) {
            this.propertyFeeId = propertyFeeId;
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

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate == null ? null : startDate.trim();
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate == null ? null : endDate.trim();
        }

        public String getCompanySpace() {
            return companySpace;
        }

        public void setCompanySpace(String companySpace) {
            this.companySpace = companySpace == null ? null : companySpace.trim();
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitprice) {
            this.unitPrice = unitprice == null ? null : unitprice.trim();
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount == null ? null : discount.trim();
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee == null ? null : fee.trim();
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment == null ? null : comment.trim();
        }

        public Integer getIsPay() {
            return isPay;
        }

        public void setIsPay(Integer isPay) {
            this.isPay = isPay;
        }

        public Integer getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(Integer delFlag) {
            this.delFlag = delFlag;
        }
    }
}
