package com.fcn.park.rent.bean;

import java.util.List;

/**
 * Created by 860617024 on 23/04/2018.
 */

public class RentInitBean {

    private List<InitItems> houstTypeList;
    private List<InitItems> statusList;

    public List<InitItems> getHoustTypeList() {
        return houstTypeList;
    }

    public void setHoustTypeList(List<InitItems> houstTypeList) {
        this.houstTypeList = houstTypeList;
    }

    public List<InitItems> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<InitItems> statusList) {
        this.statusList = statusList;
    }
}
