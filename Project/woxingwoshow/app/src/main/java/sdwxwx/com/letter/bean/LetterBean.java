package sdwxwx.com.letter.bean;


public class LetterBean {
    private String icon;
    private String name;
    private String content;
    private long createDate;
    private boolean isComMeg;
    private String mMemberId;

    public final static int RECIEVE_MSG = 0;
    public final static int SEND_MSG = 1;

    public LetterBean(String mMemberId,String icon, String name, String content,
                      long createDate, boolean isComMeg) {
        this.icon = icon;
        this.name = name;
        this.content = content;
        this.createDate = createDate;
        this.isComMeg = isComMeg;
        this.mMemberId = mMemberId;
    }

    public String getmMemberId() {
        return mMemberId;
    }

    public void setmMemberId(String mMemberId) {
        this.mMemberId = mMemberId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isComMeg() {
        return isComMeg;
    }

    public void setComMeg(boolean isComMeg) {
        this.isComMeg = isComMeg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "LetterBean [icon=" + icon + ", name=" + name + ", content="
                + content + ", createDate=" + createDate + ", isComing = " + isComMeg() + "]";
    }


}
