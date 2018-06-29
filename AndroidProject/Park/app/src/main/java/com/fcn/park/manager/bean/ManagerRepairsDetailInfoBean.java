package com.fcn.park.manager.bean;

/**
 * Created by 丁胜胜 on 2018/04/24.
 * 类描述:管理中心的报修详情用bean
 */

public class ManagerRepairsDetailInfoBean {

        private String repairId;            //报修Id
        private String repairName;          //报修人姓名
        private String repairPhone;           //报修人电话
        private String repairAddress;      //报修地址
        private String repairPic1;          //报修图片1
        private String repairPic2;          //报修图片2
        private String repairPic3;          //报修图片3
        private String repairContent;      //报修内容
        private String repairTime;         //报修时间
        private String userName;           //当前用户名

        /**
         * repairId：1
         * repairName:上官紫林
         * repairPhone : 17853487409
         * repairAddress : 齐鲁软件园中心池塘
         * repairPic1 : 图片1
         * repairPic2 : 图片2
         * repairPic3 : 图片3
         * repairContent : 鱼儿被乌龟吃了，请尽快去捉乌龟，释放小鱼苗
         * repairTime : 2018-04-13 16:06:06
         * userName：XX单位
         */

        public String getRepairId() {
            return repairId;
        }

        public void setRepairId(String repairId) {
            this.repairId = repairId;
        }

        public String getRepairName() {
            return repairName;
        }

        public void setRepairName(String repairName) {
            this.repairName = repairName;
        }

        public String getRepairPhone() {
            return repairPhone;
        }

        public void setRepairPhone(String repairPhone) {
            this.repairPhone = repairPhone;
        }

        public String getRepairAddress() {
            return repairAddress;
        }

        public void setRepairAddress(String repairAddress) {
            this.repairAddress = repairAddress;
        }

        public String getRepairPic1() {
            return repairPic1;
        }

        public void setRepairPic1(String repairPic1) {
            this.repairPic1 = repairPic1;
        }

        public String getRepairPic2() {
            return repairPic2;
        }

        public void setRepairPic2(String repairPic2) {
            this.repairPic2 = repairPic2;
        }

        public String getRepairPic3() {
            return repairPic3;
        }

        public void setRepairPic3(String repairPic3) {
            this.repairPic3 = repairPic3;
        }

        public String getRepairContent() {
            return repairContent;
        }

        public void setRepairContent(String repairContent) {
            this.repairContent = repairContent;
        }

        public String getRepairTime() {
            return repairTime;
        }

        public void setRepairTime(String repairTime) {
            this.repairTime = repairTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

}
