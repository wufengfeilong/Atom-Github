package fcn.co.jp.park.model.rent;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public class RentInfo {

    List<PageData> houstTypeList;

    List<PageData> statusList;

    public List<PageData> getHoustTypeList() {
        return houstTypeList;
    }

    public void setHoustTypeList(List<PageData> houstTypeList) {
        this.houstTypeList = houstTypeList;
    }

    public List<PageData> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<PageData> statusList) {
        this.statusList = statusList;
    }
}
