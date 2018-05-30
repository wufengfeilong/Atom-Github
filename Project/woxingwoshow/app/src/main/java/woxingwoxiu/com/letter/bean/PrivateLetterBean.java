package woxingwoxiu.com.letter.bean;

import java.util.List;

/**
 * Created by 860117073 on 2018/5/14.
 */

public class PrivateLetterBean {

    private List<LetterBean> getLetterBean;

    public List<LetterBean> getGetLetterBean() {
        return getLetterBean;
    }

    public void setGetLetterBean(List<LetterBean> getLetterBean) {
        this.getLetterBean = getLetterBean;
    }

    public static class LetterBean {

        private String userName; //用户名称

        private String letterTime; //私信时间

        private String userHead; //用户头像

        private String letterContent; //私信内容

        private boolean type; //item布局类型，true是我，false是对方

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getLetterTime() {
            return letterTime;
        }

        public void setLetterTime(String letterTime) {
            this.letterTime = letterTime;
        }

        public String getUserHead() {
            return userHead;
        }

        public void setUserHead(String userHead) {
            this.userHead = userHead;
        }

        public String getLetterContent() {
            return letterContent;
        }

        public void setLetterContent(String letterContent) {
            this.letterContent = letterContent;
        }
    }
}

