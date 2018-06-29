package com.fcn.park.property.bean;

import android.media.Image;

/**
 * Created by 860117073 on 2018/4/11.
 */

public class PropertyRepairBean {

    private String userId; //用户登录id
    private String repairName; //报修名称
    private String repairPhone; //报修电话
    private String repairAddress; //报修地址
    private String repairContent; //报修内容

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRepairName() {return repairName;}

    public void setRepairName(String repairName) {this.repairName = repairName;}

    public String getRepairPhone() {return repairPhone;}

    public void setRepairPhone(String repairPhone) {this.repairPhone = repairPhone;}

    public String getRepairAddress() {
        return repairAddress;
    }

    public void setRepairAddress(String repairAddress) {
        this.repairAddress = repairAddress;
    }

    public String getRepairContent() {return repairContent;}

    public void setRepairContent(String repairContent) {this.repairContent = repairContent;}

}
