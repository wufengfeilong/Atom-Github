package com.fcn.park.manager.bean;

/**
 * 待审批、已审批广告位详情用Bean.
 */
public class ManagerAdvertisingApprovalBean {

    /**
     * advertisingId :
     * advertisingImg :
     * advertisingLink :
     * approvalStatus : 广告审批状态：0(未审批)，1(已审批)，2(拒绝)
     * rejectReason :
     * remainDays :
     * insertUser:
     * insetTime:
     */

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
    // 插入者Id
    private String userId;
    // 插入者名
    private String insertUser;
    // 插入时间
    private String insertTime;
    // 更新者名
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

    // 是否显示“拒绝”的Flag(0：显示　1：不显示)
    private int refuseFlg;

    // 申请广告位的用户需要缴纳的广告费
    private String advertisingFee;
    // 申请广告位的用户需要缴纳广告费的标题
    private String msgTitle;
    // 申请广告位的用户需要缴纳广告费的说明内容
    private String msgContent;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getRefuseFlg() {
        return refuseFlg;
    }

    public void setRefuseFlg(int refuseFlg) {
        this.refuseFlg = refuseFlg;
    }

    public String getAdvertisingFee() {
        return advertisingFee;
    }

    public void setAdvertisingFee(String advertisingFee) {
        this.advertisingFee = advertisingFee;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
