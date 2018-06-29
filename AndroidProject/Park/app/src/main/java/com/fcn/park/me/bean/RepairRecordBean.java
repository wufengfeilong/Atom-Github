package com.fcn.park.me.bean;

import java.util.List;

/**
 * Created by 860117066 on 2018/04/17.
 * 类描述：查看个人报修列表用bean.
 */

public class RepairRecordBean {
    private String totalPage;
    private boolean isNext;

    public List<ListRecordBean> getListRecordBean() {
        return listRecordBean;
    }

    public void setListRecordBean(List<ListRecordBean> listRecordBean) {
        this.listRecordBean = listRecordBean;
    }

    private List<ListRecordBean> listRecordBean;
    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }
    /*
        * ID
        * 报修人姓名
        * 报修人电话
        * 报修人地址
        * 报修图片
        * 报修内容
        * 报修时间
        * 报修状态
        * */
    public static class ListRecordBean {
        private String repairId;
        private String repairName;
        private String repairPhone;
        private String repairAddress;
        private String repairPicture;
        private String repairContent;
        private String repairTime;
        private String repairState;

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

        public String getRepairPicture() {
            return repairPicture;
        }

        public void setRepairPicture(String repairPicture) {
            this.repairPicture = repairPicture;
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

        public String getRepairState() {
            return repairState;
        }

        public void setRepairState(String repairState) {
            this.repairState = repairState;
        }
    }
}
