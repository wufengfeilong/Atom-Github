package woxingwoxiu.com.login.bean;

/**
 * Created by 丁胜胜 on 2018/05/11.
 */

public class LoginVerifyBean {

    public static class VerifyBean {

        private String phoneNum;       //手机号
        private String phoneNumSpace; //手机号 3-4-4格式


        public String getPhoneNumSpace() {
            return phoneNumSpace;
        }

        public void setPhoneNumSpace(String phoneNumSpace) {
            this.phoneNumSpace = phoneNumSpace;
        }



        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }
    }
}
