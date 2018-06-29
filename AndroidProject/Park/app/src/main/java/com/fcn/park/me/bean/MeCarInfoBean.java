package com.fcn.park.me.bean;

import java.util.List;

/**
 * Created by 860117073 on 2018/4/25.
 */

public class MeCarInfoBean {

    private List<CarInfoBean> getCarInfo;

    private boolean isNext;

    public List<CarInfoBean> getGetCarInfo() {
        return getCarInfo;
    }

    public void setGetCarInfo(List<CarInfoBean> getCarInfo) {
        this.getCarInfo = getCarInfo;
    }

    public boolean isIsNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }

    public static class CarInfoBean{

        private String carOwner;  //车主姓名

        private String plateNumber;  //车牌号

        private String phone;  //手机号

        private String carId;   //车辆id

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getCarOwner() {
            return carOwner;
        }

        public void setCarOwner(String carOwner) {
            this.carOwner = carOwner;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

    }
}
