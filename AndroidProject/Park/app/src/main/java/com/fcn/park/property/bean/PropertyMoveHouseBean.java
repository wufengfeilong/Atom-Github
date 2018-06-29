package com.fcn.park.property.bean;

import java.util.List;

/**
 * Created by 860117073 on 2018/4/18.
 */

public class PropertyMoveHouseBean {

    private List<MoveHouseBean> getMoveHouse;

    private boolean isNext;

    public List<MoveHouseBean> getGetMoveHouse() {
        return getMoveHouse;
    }

    public void setGetMoveHouse(List<MoveHouseBean> getMoveHouse) {
        this.getMoveHouse = getMoveHouse;
    }

    public boolean isIsNext() {
        return isNext;
    }

    public void setIsNext(boolean isNext) {
        this.isNext = isNext;
    }

    public static class MoveHouseBean{

        private String businessName; //商家名称
        private String synopsis; //简介
        private String phone; //电话号码
        private String address; //地址

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
