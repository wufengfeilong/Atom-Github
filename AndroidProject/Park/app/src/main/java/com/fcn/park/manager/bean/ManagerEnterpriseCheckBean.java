package com.fcn.park.manager.bean;

import java.util.List;

/**
 * 管理中心的企业审核管理功能用Bean
 */
public class ManagerEnterpriseCheckBean {

    private String totalPage;
    private boolean isNext;
    private List<EnterpriseCheckListBean> enterpriseCheckList;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }

    public List<EnterpriseCheckListBean> getEnterpriseCheckList() {
        return enterpriseCheckList;
    }

    public void setEnterpriseCheckList(List<EnterpriseCheckListBean> enterpriseCheckList) {
        this.enterpriseCheckList = enterpriseCheckList;
    }

    public static class EnterpriseCheckListBean {

    }

}

