package com.fcn.park.property.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 用于微信支付的时候，获取服务端返回数据的实体类
 */

public class WxPayBean {


    /**
     * callAppParam : {"appid":"wxf1f068fec5ae2ae0","noncestr":"8a00e281f8abab646cef687e80fa1c15","package":"Sign=WXPay","partnerid":"1348693101","prepayid":"wx201705191111343e4095b72a0886499204","sign":"2E0367420CEE4C53B16B64767217896C","timestamp":"1495163434"}
     * otherParam : {"out_trade_no":"1495163434137958","total_fee":"10000","app_id":"wxf1f068fec5ae2ae0","prepay_id":"wx201705191111343e4095b72a0886499204"}
     */

    private CallAppParamBean callAppParam;
    private OtherParamBean otherParam;

    public CallAppParamBean getCallAppParam() {
        return callAppParam;
    }

    public void setCallAppParam(CallAppParamBean callAppParam) {
        this.callAppParam = callAppParam;
    }

    public OtherParamBean getOtherParam() {
        return otherParam;
    }

    public void setOtherParam(OtherParamBean otherParam) {
        this.otherParam = otherParam;
    }

    public static class CallAppParamBean {
        /**
         * appid : wxf1f068fec5ae2ae0
         * noncestr : 8a00e281f8abab646cef687e80fa1c15
         * package : Sign=WXPay
         * partnerid : 1348693101
         * prepayid : wx201705191111343e4095b72a0886499204
         * sign : 2E0367420CEE4C53B16B64767217896C
         * timestamp : 1495163434
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class OtherParamBean {
        /**
         * out_trade_no : 1495163434137958
         * total_fee : 10000
         * app_id : wxf1f068fec5ae2ae0
         * prepay_id : wx201705191111343e4095b72a0886499204
         */

        private String out_trade_no;
        private String total_fee;
        private String app_id;
        private String prepay_id;

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }
    }
}
