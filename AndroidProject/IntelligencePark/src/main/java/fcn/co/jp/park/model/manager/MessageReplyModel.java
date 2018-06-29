package fcn.co.jp.park.model.manager;

/**
 * Created by 丁胜胜 on 2018/04/26.
 * 描述：管理中心留言列表用Model
 */
public class MessageReplyModel {

    private String suggestionId;      //编号
    private String userName;           //当前用户名
    private String describeContent;  //投诉或建议内容
    private String answerContent;    //留言回复内容
    private String updateTimestamp;    //投诉或建议时间
    private String updateUser;
    private String replyStatus;

    public String getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(String replyStatus) {
        this.replyStatus = replyStatus;
    }



    public String getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(String suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getDescribeContent() {
        return describeContent;
    }

    public void setDescribeContent(String describeContent) {
        this.describeContent = describeContent;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}
