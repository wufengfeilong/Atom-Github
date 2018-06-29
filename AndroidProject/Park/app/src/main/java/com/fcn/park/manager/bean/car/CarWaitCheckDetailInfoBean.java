package com.fcn.park.manager.bean.car;

/**
 * 类描述：月租车辆审批详情画面用Bean
 */

public class CarWaitCheckDetailInfoBean {

    // 月租车辆id
    private String parkPay_id;
    // 姓名
    private String applicantName;
    // 电话
    private String phone;
    // 公司名称
    private String company;
    // 申请类型
    private String applyType;
    // 开始时间
    private String startDate;
    // 结束时间
    private String endDate;
    // 审批详情
    private String checkinfo;
    // 在职证明
    private String onJobProImage;
    // 驾驶证
    private String driverCardImage;
    // 行驶证
    private String driveringCardImage;

    public String getParkPay_id() {
        return parkPay_id;
    }

    public void setarkPay_id(String parkPay_id) {
        this.parkPay_id = parkPay_id;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCheckinfo() {
        return checkinfo;
    }

    public void setCheckinfo(String checkinfo) {
        this.checkinfo = checkinfo;
    }

    public String getOnJobProImage() {
        return onJobProImage;
    }

    public void setOnJobProImage(String onJobProImage) {
        this.onJobProImage = onJobProImage;
    }
    public String getDriverCardImage() {
        return driverCardImage;
    }

    public void setDriverCardImage(String driverCardImage) {
        this.driverCardImage = driverCardImage;
    }
    public String getDriveringCardImage() {
        return driveringCardImage;
    }

    public void setDriveringCardImage(String driveringCardImage) {
        this.driveringCardImage = driveringCardImage;
    }
}
