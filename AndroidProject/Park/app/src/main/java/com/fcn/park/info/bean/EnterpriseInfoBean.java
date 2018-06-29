package com.fcn.park.info.bean;

import com.fcn.park.base.http.ParamNames;

import java.util.List;

/**
 * Created by liuyq on 2017/04/18.
 * 类描述：企业详情页面
 */

public class EnterpriseInfoBean {

    /**
     * {"msg":"企业信息详情",
     * "result":true,
     * "data":{"comPictUploadList":[{"companyId":"0001","name":"Jellyfish.jpg","environmentPicture":"uploadFiles/companyShowImg/64c6f18a16c04f5998beb475c4554551.jpg","id":1},
     * {"companyId":"0001","name":"Desert.jpg","environmentPicture":"uploadFiles/companyShowImg/b3e1f5dbccd1420dabec4afb62f6e7f2.jpg","id":2},
     * {"companyId":"0001","name":"Hydrangeas.jpg","environmentPicture":"uploadFiles/companyShowImg/f1bf426f20004a99acc3109279a7e9b5.jpg","id":3}],
     * "businessinfo":{"companyIntroduction":"sssssssssssssssssssssssssss","address":"齐鲁","mail":"123@test.com","companyName":"服侍","contact":"张","industry":"培训机构","userId":"0001","contactTel":"13256799736"}}}
     */
    private BusinessinfoBean businessinfo;
    private List<ComPicUpLoadList> comPictUploadList;

    public List<ComPicUpLoadList> getComPictUploadList() {
        return comPictUploadList;
    }

    public void setComPictUploadList(List<ComPicUpLoadList> comPictUploadList) {
        this.comPictUploadList = comPictUploadList;
    }

    public BusinessinfoBean getBusinessinfo() {
        return businessinfo;
    }

    public void setBusinessinfo(BusinessinfoBean businessinfo) {
        this.businessinfo = businessinfo;
    }

    public static class BusinessinfoBean {

        private String companyIntroduction;
        private String mail;
        private String companyName;
        private String userId;
        private String contactTel;
        private String contact;
        private String logo;
        private String industry;
        private String address;

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getCompanyIntroduction() {
            return companyIntroduction;
        }

        public void setCompanyIntroduction(String companyIntroduction) {
            this.companyIntroduction = companyIntroduction;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getCompanyName() {
            return companyName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getContactTel() {
            return contactTel;
        }

        public void setContactTel(String contactTel) {
            this.contactTel = contactTel;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }


        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class ComPicUpLoadList {
        private String environmentPicture;
        private String companyId;
        @ParamNames("namePic")
        private String name;
        private int id;

        public String getEnvironmentPicture() {
            return environmentPicture;
        }

        public void setEnvironmentPicture(String environmentPicture) {
            this.environmentPicture = environmentPicture;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
