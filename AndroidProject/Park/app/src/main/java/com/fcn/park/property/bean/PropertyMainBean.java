package com.fcn.park.property.bean;

import com.fcn.park.base.http.ParamNames;

import java.io.Serializable;

/**
 * 缴费模块用Bean
 */

public class PropertyMainBean implements Serializable {

    // 企业名称
    @ParamNames("user_name")
    private String companyStr;

    // 缴费类型
    @ParamNames("payType")
    private int payType;

    // 水费应缴金额
    @ParamNames("water_fee")
    private String waterPayNumStr;

    // 水表起码
    @ParamNames("start_num")
    private String waterStartNumStr;

    // 水表止码
    @ParamNames("end_num")
    private String waterEndNumStr;

    // 水表录表日期
    @ParamNames("record_date")
    private String waterRecordDate;

    // 电费应缴金额
    @ParamNames("electric_fee")
    private String electricPayNumStr;

    // 电表起码
    @ParamNames("electric_start_num")
    private String electricStartNumStr;

    // 电表止码
    @ParamNames("electric_end_num")
    private String electricEndNumStr;

    // 电表录表日期
    @ParamNames("electric_record_date")
    private String electricRecordDate;

    // 物业费应缴金额
    @ParamNames("property_fee")
    private String propertyPayNumStr;

    // 物业费起始日期
    @ParamNames("propertyfee_startDate")
    private String propertyStartDate;

    // 物业费终止日期
    @ParamNames("propertyfee_endDate")
    private String propertyEndDate;

    // 物业费折扣
    @ParamNames("property_discount")
    private String propertyDiscount;

    // 租赁费应缴金额
    @ParamNames("rent_fee")
    private String rentNumStr;

    // 租赁面积
    @ParamNames("area")
    private String rentAreaStr;

    // 租赁单价
    @ParamNames("unit_price")
    private String rentUnitPrice;

    // 租赁费起始时间
    @ParamNames("rentfee_startDate")
    private String rentStartDate;

    // 租赁费终止时间
    @ParamNames("rentfee_endDate")
    private String rentEndDate;

    // 租赁费折扣
    @ParamNames("rent_discount")
    private String rentDiscount;

    // 停车费缴费金额
    @ParamNames("paymentmoney")
    private String parkingPayNum;

    // 车牌号码
    @ParamNames("carNumber")
    private String plateNum;

    // 入场时间
    @ParamNames("enterTime")
    private String enterTime;

    // 停车时长
    @ParamNames("timeLength")
    private String timeLength;

    // 离开时间
    @ParamNames("exitTime")
    private String exitTime;

    // 停车卡起始日期
    @ParamNames("startDate")
    private String startDate;

    // 停车卡终止日期
    @ParamNames("endDate")
    private String endDate;

    // 停车卡类型
    @ParamNames("applyType")
    private String applyType;

    // 停车卡申请审核状态
    @ParamNames("parkStatus")
    private String checkStatus;

    // 缴费时间
    @ParamNames("paymenttime")
    private String paymentTime;

    // 支付方式 0：支付宝 1：微信
    @ParamNames("paymentway")
    private String paymentWay;

    // 停车缴费ID
    @ParamNames("parkPayId")
    private int parkPayId;

    // 缴费状态
    private boolean payStatus;

    /**
     * @return 企业名称
     */
    public String getCompanyStr() {
        return companyStr;
    }

    /**
     * @param companyStr 企业名称
     */
    public void setCompanyStr(String companyStr) {
        this.companyStr = companyStr;
    }

    /**
     * @return 缴费类型
     */
    public int getPayType() {
        return payType;
    }

    /**
     * @param payType 缴费类型
     */
    public void setPayType(int payType) {
        this.payType = payType;
    }

    /**
     * @return 水费应缴金额
     */
    public String getWaterPayNumStr() {
        return waterPayNumStr;
    }

    /**
     * @param waterPayNumStr 水费应缴金额
     */
    public void setWaterPayNumStr(String waterPayNumStr) {
        this.waterPayNumStr = waterPayNumStr;
    }

    /**
     * @return 水表起码
     */
    public String getWaterStartNumStr() {
        return waterStartNumStr;
    }

    /**
     * @param waterStartNumStr 水表起码
     */
    public void setWaterStartNumStr(String waterStartNumStr) {
        this.waterStartNumStr = waterStartNumStr;
    }

    /**
     * @return 水表止码
     */
    public String getWaterEndNumStr() {
        return waterEndNumStr;
    }

    /**
     * @param waterEndNumStr 水表止码
     */
    public void setWaterEndNumStr(String waterEndNumStr) {
        this.waterEndNumStr = waterEndNumStr;
    }

    /**
     * @return 水表录表日期
     */
    public String getWaterRecordDate() {
        return waterRecordDate;
    }

    /**
     * @param waterRecordDate 水表录表日期
     */
    public void setWaterRecordDate(String waterRecordDate) {
        this.waterRecordDate = waterRecordDate;
    }

    /**
     * @return 电费应缴金额
     */
    public String getElectricPayNumStr() {
        return electricPayNumStr;
    }

    /**
     * @param electricPayNumStr 电费应缴金额
     */
    public void setElectricPayNumStr(String electricPayNumStr) {
        this.electricPayNumStr = electricPayNumStr;
    }

    /**
     * @return 电表起码
     */
    public String getElectricStartNumStr() {
        return electricStartNumStr;
    }

    /**
     * @param electricStartNumStr 电表起码
     */
    public void setElectricStartNumStr(String electricStartNumStr) {
        this.electricStartNumStr = electricStartNumStr;
    }

    /**
     * @return 电表止码
     */
    public String getElectricEndNumStr() {
        return electricEndNumStr;
    }

    /**
     * @param electricEndNumStr 电表止码
     */
    public void setElectricEndNumStr(String electricEndNumStr) {
        this.electricEndNumStr = electricEndNumStr;
    }

    /**
     * @return 物业费应缴金额
     */
    public String getElectricRecordDate() {
        return electricRecordDate;
    }

    /**
     * @param electricRecordDate 物业费应缴金额
     */
    public void setElectricRecordDate(String electricRecordDate) {
        this.electricRecordDate = electricRecordDate;
    }

    /**
     * @return 物业费应缴金额
     */
    public String getPropertyPayNumStr() {
        return propertyPayNumStr;
    }

    /**
     * @param propertyPayNumStr 物业费应缴金额
     */
    public void setPropertyPayNumStr(String propertyPayNumStr) {
        this.propertyPayNumStr = propertyPayNumStr;
    }

    /**
     * @return 物业费起始日期
     */
    public String getPropertyStartDate() {
        return propertyStartDate;
    }

    /**
     * @param propertyStartDate 物业费起始日期
     */
    public void setPropertyStartDate(String propertyStartDate) {
        this.propertyStartDate = propertyStartDate;
    }

    /**
     * @return 物业费终止日期
     */
    public String getPropertyEndDate() {
        return propertyEndDate;
    }

    /**
     * @param propertyEndDate 物业费终止日期
     */
    public void setPropertyEndDate(String propertyEndDate) {
        this.propertyEndDate = propertyEndDate;
    }

    /**
     * @return 物业费折扣
     */
    public String getPropertyDiscount() {
        return propertyDiscount;
    }

    /**
     * @param propertyDiscount 物业费折扣
     */
    public void setPropertyDiscount(String propertyDiscount) {
        this.propertyDiscount = propertyDiscount;
    }

    /**
     * @return 租赁费应缴金额
     */
    public String getRentNumStr() {
        return rentNumStr;
    }

    /**
     * @param rentNumStr 租赁费应缴金额
     */
    public void setRentNumStr(String rentNumStr) {
        this.rentNumStr = rentNumStr;
    }

    /**
     * @return 租赁面积
     */
    public String getRentAreaStr() {
        return rentAreaStr;
    }

    /**
     * @param rentAreaStr 租赁面积
     */
    public void setRentAreaStr(String rentAreaStr) {
        this.rentAreaStr = rentAreaStr;
    }

    /**
     * @return 租赁单价
     */
    public String getRentUnitPrice() {
        return rentUnitPrice;
    }

    /**
     * @param rentUnitPrice 租赁单价
     */
    public void setRentUnitPrice(String rentUnitPrice) {
        this.rentUnitPrice = rentUnitPrice;
    }

    /**
     * @return 租赁费起始时间
     */
    public String getRentStartDate() {
        return rentStartDate;
    }

    /**
     * @param rentStartDate 租赁费起始时间
     */
    public void setRentStartDate(String rentStartDate) {
        this.rentStartDate = rentStartDate;
    }

    /**
     * @return 租赁费终止时间
     */
    public String getRentEndDate() {
        return rentEndDate;
    }

    /**
     * @param rentEndDate 租赁费终止时间
     */
    public void setRentEndDate(String rentEndDate) {
        this.rentEndDate = rentEndDate;
    }

    /**
     * @return 租赁费折扣
     */
    public String getRentDiscount() {
        return rentDiscount;
    }

    /**
     * @param rentDiscount 租赁费折扣
     */
    public void setRentDiscount(String rentDiscount) {
        this.rentDiscount = rentDiscount;
    }

    /**
     * @return 停车费缴费金额
     */
    public String getParkingPayNum() {
        return parkingPayNum;
    }

    /**
     * @param parkingPayNum 停车费缴费金额
     */
    public void setParkingPayNum(String parkingPayNum) {
        this.parkingPayNum = parkingPayNum;
    }

    /**
     * @return 车牌号码
     */
    public String getPlateNum() {
        return plateNum;
    }

    /**
     * @param plateNum 车牌号码
     */
    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    /**
     * @return 入场时间
     */
    public String getEnterTime() {
        return enterTime;
    }

    /**
     * @param enterTime 入场时间
     */
    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    /**
     * @return 停车时长
     */
    public String getTimeLength() {
        return timeLength;
    }

    /**
     * @param timeLength 停车时长
     */
    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }

    /**
     * @return 离开时间
     */
    public String getExitTime() {
        return exitTime;
    }

    /**
     * @param exitTime 离开时间
     */
    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    /**
     * @return 停车卡起始日期
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate 停车卡起始日期
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return 停车卡终止日期
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate 停车卡终止日期
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return 停车卡类型
     */
    public String getApplyType() {
        return applyType;
    }

    /**
     * @param applyType 停车卡类型
     */
    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    /**
     * @return 停车卡申请审核状态
     */
    public String getCheckStatus() {
        return checkStatus;
    }

    /**
     * @param checkStatus 停车卡申请审核状态
     */
    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    /**
     * @return 停车费缴费时间
     */
    public String getPaymentTime() {
        return paymentTime;
    }

    /**
     * @param paymentTime 停车费缴费时间
     */
    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    /**
     * @return 支付方式
     */
    public String getPaymentWay() {
        return paymentWay;
    }

    /**
     * @param paymentWay 支付方式
     */
    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }

    /**
     * @return 停车缴费ID
     */
    public int getParkPayId() {
        return parkPayId;
    }

    /**
     * @param parkPayId 停车缴费ID
     */
    public void setParkPayId(int parkPayId) {
        this.parkPayId = parkPayId;
    }

    /**
     * @return 缴费状态
     */
    public boolean isPayStatus() {
        return payStatus;
    }

    /**
     * @param payStatus 缴费状态
     */
    public void setPayStatus(boolean payStatus) {
        this.payStatus = payStatus;
    }

}
