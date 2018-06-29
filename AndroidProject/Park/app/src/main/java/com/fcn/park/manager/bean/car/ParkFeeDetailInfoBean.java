package com.fcn.park.manager.bean.car;

/**
 * 管理中心的停车付费详情画面用Bean
 */

public class ParkFeeDetailInfoBean {

    // 车辆id
    private String parkPay_id;
    // 姓名
    private String applicantName;
    // 电话
    private String phone;
    // 车牌号
    private String carNumber;
    // 缴费金额
    private String paymentmoney;
    // 缴费时间
    private String paymenttime;
    // 缴费方式
    private String paymentway;


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

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getPaymentmoney() {
        return paymentmoney;
    }

    public void setPaymentmoney(String paymentmoney) {
        this.paymentmoney = paymentmoney;
    }

    public String getPaymenttime() {
        return paymenttime;
    }

    public void setPaymenttime(String paymenttime) {
        this.paymenttime = paymenttime;
    }

    public String getPaymentway() {
        return paymentway;
    }

    public void setPaymentway(String paymentway) {
        this.paymentway = paymentway;
    }


}
