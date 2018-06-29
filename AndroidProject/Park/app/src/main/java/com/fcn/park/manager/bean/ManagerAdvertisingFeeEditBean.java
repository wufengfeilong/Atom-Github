package com.fcn.park.manager.bean;

import java.util.List;

/**
 * 广告费用编辑用数据Bean.
 */
public class ManagerAdvertisingFeeEditBean {

    private List<AdvertisingFeeList> advertisingFeeList;

    public List<AdvertisingFeeList> getAdvertisingFeeList() {
        return advertisingFeeList;
    }

    public void setAdvertisingFeeList(List<AdvertisingFeeList> advertisingFeeList) {
        this.advertisingFeeList = advertisingFeeList;
    }

    public static class AdvertisingFeeList {
        /**
         * 广告套餐的费用值
         */
        // distinguish_value
        private String advertiseSetFee;

        /**
         * 广告套餐的类型名字：套餐一（一个月）、套餐二（三个月）、套餐三（一年）
         */
        // distinguish_name
        private String advertiseSetName;

        /**
         * 广告套餐的Id
         */
        // distinguish_id
        private String advertiseSetId;

        /**
         * 广告套餐的类型Key
         */
        // distinguish_key
        private String advertiseSetType;

        public String getAdvertiseSetFee() {
            return advertiseSetFee;
        }

        public void setAdvertiseSetFee(String advertiseSetFee) {
            this.advertiseSetFee = advertiseSetFee;
        }

        public String getAdvertiseSetName() {
            return advertiseSetName;
        }

        public void setAdvertiseSetName(String advertiseSetName) {
            this.advertiseSetName = advertiseSetName;
        }

        public String getAdvertiseSetId() {
            return advertiseSetId;
        }

        public void setAdvertiseSetId(String advertiseSetId) {
            this.advertiseSetId = advertiseSetId;
        }

        public String getAdvertiseSetType() {
            return advertiseSetType;
        }

        public void setAdvertiseSetType(String advertiseSetType) {
            this.advertiseSetType = advertiseSetType;
        }
    }
}
