package com.fcn.park.manager.bean;

import java.util.List;

/**
 * 广告位待审核、已审核列表共用List.
 */
public class ManagerAdvertisingApprovalListBean {

    private String totalPage;

    private boolean isNext;

    private List<ListAdvertisingApprovalBean> listAdvertisingApproval;

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

    public List<ListAdvertisingApprovalBean> getListAdvertisingApproval() {
        return listAdvertisingApproval;
    }

    public void setListAdvertisingApproval(List<ListAdvertisingApprovalBean> listAdvertisingApproval) {
        this.listAdvertisingApproval = listAdvertisingApproval;
    }

    public static class ListAdvertisingApprovalBean {

        // 画面显示的序号
        private String no;
        // 广告ID
        private String advertisingId;
        // 广告图片
        private String advertisingImg;
        // 广告的说明
        private String advertisingComment;
        // 广告审批状态：0(未审批)，1(已审批),2(拒绝)
        private String approvalStatus;
        // 审核通过时的时期
        private String approvalDate;
        // 拒绝理由
        private String rejectReason;
        // 插入者
        private String insertUser;
        // 插入时间
        private String insertTime;
        // 更新者
        private String updateUser;
        // 更新时间
        private String updateTime;

        // 广告租赁的剩余天数（需要计算：）
        private String endDays;

        // 广告套餐的类型（与m_distinguish表中的ADVERTISE_SET_TYPE对应）
        private String setType;
        // 广告费用支付状态（0：未支付 1：已支付）
        private int payStatus;
        // 广告费用支付时间
        private String payTime;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getAdvertisingId() {
            return advertisingId;
        }

        public void setAdvertisingId(String advertisingId) {
            this.advertisingId = advertisingId;
        }

        public String getAdvertisingImg() {
            return advertisingImg;
        }

        public void setAdvertisingImg(String advertisingImg) {
            this.advertisingImg = advertisingImg;
        }

        public String getAdvertisingComment() {
            return advertisingComment;
        }

        public void setAdvertisingComment(String advertisingComment) {
            this.advertisingComment = advertisingComment;
        }

        public String getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
        }

        public String getRejectReason() {
            return rejectReason;
        }

        public void setRejectReason(String rejectReason) {
            this.rejectReason = rejectReason;
        }

        public String getApprovalDate() {
            return approvalDate;
        }

        public void setApprovalDate(String approvalDate) {
            this.approvalDate = approvalDate;
        }

        public String getInsertUser() {
            return insertUser;
        }

        public void setInsertUser(String insertUser) {
            this.insertUser = insertUser;
        }

        public String getInsertTime() {
            return insertTime;
        }

        public void setInsertTime(String insertTime) {
            this.insertTime = insertTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getEndDays() {
            return endDays;
        }

        public void setEndDays(String endDays) {
            this.endDays = endDays;
        }

        public String getSetType() {
            return setType;
        }

        public void setSetType(String setType) {
            this.setType = setType;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }
    }
}
