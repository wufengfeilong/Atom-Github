package com.fcn.park.me.bean;

/**
 * 类描述：报修详情画面用Bean.
 */

public class RepairRecordDetailBean {
    private String repairId;            //ID
    private String repairName;          //报修人姓名
    private String repairPhone;         //报修人电话
    private String repairAddress;       //报修人地址
    private String repairPic1;          //报修图片1
    private String repairPic2;          //报修图片2
    private String repairPic3;          //报修图片3
    private String repairContent;       //报修内容
    private String repairTime;          //报修时间

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }

    public String getRepairPhone() {
        return repairPhone;
    }

    public void setRepairPhone(String repairPhone) {
        this.repairPhone = repairPhone;
    }

    public String getRepairAddress() {
        return repairAddress;
    }

    public void setRepairAddress(String repairAddress) {
        this.repairAddress = repairAddress;
    }

    public String getRepairPic1() {
        return repairPic1;
    }

    public void setRepairPic1(String repairPic1) {
        this.repairPic1 = repairPic1;
    }

    public String getRepairPic2() {
        return repairPic2;
    }

    public void setRepairPic2(String repairPic2) {
        this.repairPic2 = repairPic2;
    }

    public String getRepairPic3() {
        return repairPic3;
    }

    public void setRepairPic3(String repairPic3) {
        this.repairPic3 = repairPic3;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }
}
