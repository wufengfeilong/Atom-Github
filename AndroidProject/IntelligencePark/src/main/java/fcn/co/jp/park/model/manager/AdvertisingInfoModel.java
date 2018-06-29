package fcn.co.jp.park.model.manager;

public class AdvertisingInfoModel {

    private Integer advertisingId;  // 广告ID

    private String advertisingImg; // 广告图片

    private String advertisingComment; // 广告的说明

    private int setType;        // 广告套餐的类型（与m_distinguish表中的ADVERTISE_SET_TYPE对应）

    private int approvalStatus; // 广告审批状态：0(未审批)，1(已审批),2(拒绝)

    private String approvalDate; // 审核通过时的时期

    private String rejectReason; // 拒绝理由

    private int payStatus;     // 广告费用支付状态（0：未支付　1：已支付）

    private String payTime;     // 广告费用支付时间

    private String insertUser; // 插入者Id

    private String insertTime; // 插入时间

    private String updateUser; // 更新者Id

    private String updateTime; // 更新时间

    private String advertisingFee; // 用户需要缴纳的广告费用

    public Integer getAdvertisingId() {
        return advertisingId;
    }

    public void setAdvertisingId(Integer advertisingId) {
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

    public int getSetType() {
        return setType;
    }

    public void setSetType(int setType) {
        this.setType = setType;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getAdvertisingFee() {
        return advertisingFee;
    }

    public void setAdvertisingFee(String advertisingFee) {
        this.advertisingFee = advertisingFee;
    }
}
