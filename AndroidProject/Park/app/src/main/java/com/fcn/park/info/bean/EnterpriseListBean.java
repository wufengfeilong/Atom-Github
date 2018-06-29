package com.fcn.park.info.bean;

import java.util.List;

/**
 * Created by liuyq on 2018/04/16.
 * 类描述：企业列表页面
 */

public class EnterpriseListBean {


    /**
     * totalPage : 1
     * isNext : false
     * businesslist : [{"addressProvince":"山东省","enterpriseNature":"民营企业","companyId":"e93db86b574da0cb",
     * "companyName":"济南东泰兴工艺品有限公司","contact":"潘东升","logo":"uploadFiles/registerImg/d73412318cce420790a58a219d74c162.png",
     * "contactTel":"18769785311","addressCity":"济南市"},{"addressProvince":"山东省","enterpriseNature":"民营企业",
     * "companyId":"6358ed82974d20e5","companyName":"济南迪安医学检验中心有限公司","contact":"韩敏","logo":"uploadFiles/registerImg/f198fa4b8af54cf89ba2a97ea0f68b49.png",
     * "contactTel":"0531-83170701","addressCity":"济南市"},
     * {"addressProvince":"山东省","enterpriseNature":"民营企业","companyId":"c5363c6420236d22","companyName":"山东富华车桥有限公司",
     * "contact":"刘艳","logo":"uploadFiles/registerImg/50f5581e21c04647b254f77aad581a19.jpg","contactTel":"0531-83831182","addressCity":"济南市"}]
     */

    private String totalPage;
    private boolean isNext;
    private List<BusinesslistBean> businesslist;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isIsNext() {
        return isNext;
    }

    public void setIsNext(boolean isNext) {
        this.isNext = isNext;
    }

    public List<BusinesslistBean> getBusinesslist() {
        return businesslist;
    }

    public void setBusinesslist(List<BusinesslistBean> businesslist) {
        this.businesslist = businesslist;
    }

    public static class BusinesslistBean {
        /**
         * enterpriseNature : 民营企业
         * companyId : e93db86b574da0cb
         * companyName : 济南东泰兴工艺品有限公司
         * contact : 潘东升
         * logo : uploadFiles/registerImg/d73412318cce420790a58a219d74c162.png
         * contactTel : 18769785311
         * address : 济南市
         */


        private String companyId;
        private String companyName;
        private String contact;
        private String logo;
        private String contactTel;
        private String address;

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
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

        public String getContactTel() {
            return contactTel;
        }

        public void setContactTel(String contactTel) {
            this.contactTel = contactTel;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
