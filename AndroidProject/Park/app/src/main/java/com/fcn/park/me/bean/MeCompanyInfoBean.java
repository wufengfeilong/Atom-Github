package com.fcn.park.me.bean;

/**
 * create by 860115039
 * date      2018/04/17
 * time      10:13
 * 个人中心-企业认证公司信息编辑用Bean
 */
public class MeCompanyInfoBean {

    private String userId;        //公司ID
    private String companyName;   //公司名
    private String industry;      //公司行业范围
    private String address;       //详细地址
    private String tel;           //电话
    private String contact;       //联系人
    private String mail;          //邮箱
    private String contents;       //详情介绍
    private String imgId;         //营业执照ID
    private String logoId;        //logoID
    private String auditTime;     //承认时间
    private String auditResult;   //承认结果：0，未处理；1，承认通过；2，承认失败
    private String auditReason;   //承认未通过的理由
    private String insertUser;    //插入者
    private String insertTime;    //插入时间（企业认证申请时间）
    private String updateUser;    //更新者
    private String updateTime;    //更新时间
    private String flg;//企业更新还是新规

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getLogoId() {
        return logoId;
    }

    public void setLogoId(String logoId) {
        this.logoId = logoId;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
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

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }
}
