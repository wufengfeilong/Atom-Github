package sdwxwx.com.play.bean;

/**
 * create by 860115039
 * date      2018/5/23
 * time      16:47
 */
public class CommentBean {

    int id;
    int member_id;
    String avatar_url;
    int comment_id;	//	评论编号。即被回复的目标评论的编号。
    String nickname;
    String content;
    String is_liked; // 会员是否点赞过该评论。
    int reply_count;//	被回复的次数。
    int like_count;
    String create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getNikename() {
        return nickname;
    }

    public void setNikename(String nikename) {
        this.nickname = nikename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }
}
