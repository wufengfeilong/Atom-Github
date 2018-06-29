package com.fcn.park.property.bean;

import android.media.Image;
import android.view.View;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 860115032 on 2018/04/08.
 */

public class PropertyParkPayBean {

    private String applicantName;

    private String company;

    private String phone;

    private String carNumber;

    private String onJobProImage;

    private String onJobProImagePath;

    private String driverCardImage;

    private String driverCardImagePath;

    private String driveringCardImage;

    private String driveringCardImagePath;

    private String applyType;

    private String applyTypeInfo;

    private String startDate;

    private String endDate;

    private String userId;

    private String money;

    private String parkStatus;

    private String checkinfo;


    public String getDriverCardImagePath() {
        return driverCardImagePath;
    }

    public void setDriverCardImagePath(String driverCardImagePath) {
        this.driverCardImagePath = driverCardImagePath;
    }

    public String getDriveringCardImagePath() {
        return driveringCardImagePath;
    }

    public void setDriveringCardImagePath(String driveringCardImagePath) {
        this.driveringCardImagePath = driveringCardImagePath;
    }

    public String getApplyTypeInfo() {
        return applyTypeInfo;
    }

    public void setApplyTypeInfo(String applyTypeInfo) {
        this.applyTypeInfo = applyTypeInfo;
    }

    public String getCheckinfo() {
        return checkinfo;
    }

    public void setCheckinfo(String checkinfo) {
        this.checkinfo = checkinfo;
    }

    public String getOnJobProImagePath() {
        return onJobProImagePath;
    }

    public void setOnJobProImagePath(String onJobProImagePath) {
        this.onJobProImagePath = onJobProImagePath;
    }

    public String getParkStatus() {
        return parkStatus;
    }

    public void setParkStatus(String parkStatus) {
        this.parkStatus = parkStatus;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
}
