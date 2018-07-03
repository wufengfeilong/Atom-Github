package sdwxwx.com.login.bean;

/**
 * Created by 丁胜胜 on 2018/05/11.
 */

public class LoginVerifyBean {

    public static class VerifyBean {

        /**
         * 手机号
         */
        private String phoneNum;
        /**
         * 手机号 3-4-4格式
         */
        private String phoneNumSpace;
        /**
         * QQID
         */
        private String qqId;
        /**
         * 微信ID
         */
        private String wechatId;
        /**
         * 用户名
         */
        private String userName;
        /**
         * 用户头像
         */
        private String userIcon;
        /**
         * 性别
         */
        private String userGender;

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getPhoneNumSpace() {
            return phoneNumSpace;
        }

        public void setPhoneNumSpace(String phoneNumSpace) {
            this.phoneNumSpace = phoneNumSpace;
        }

        public String getQqId() {
            return qqId;
        }

        public void setQqId(String qqId) {
            this.qqId = qqId;
        }

        public String getUserGender() {
            return userGender;
        }

        public void setUserGender(String userGender) {
            this.userGender = userGender;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getWechatId() {
            return wechatId;
        }

        public void setWechatId(String wechatId) {
            this.wechatId = wechatId;
        }
    }
}
