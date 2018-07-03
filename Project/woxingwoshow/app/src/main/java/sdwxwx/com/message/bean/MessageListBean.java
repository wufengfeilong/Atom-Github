package sdwxwx.com.message.bean;

import java.util.List;


/**
 * Created by 860117073 on 2018/5/11.
 */

public class MessageListBean {

    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public static class Result {

        private int ksyun_id; //作品金山云编号
        // 会员编号
        private String related_member_id;
        // 关联会员编号
        private String member_id;
        // 视频编号
        private String video_id;
        // 视频封面的URL链接
        private String cover_url;
        //布局类型
        private String type;
        //标题
        private String title;
        //内容
        private String content;
        //用户头像
        private String avatar_url;
        //用户名称
        private String nickname;
        //创建时间
        private String create_time;
        //是否关注
        private boolean is_followed;
        //播放数量
        private String video_count;
        //粉丝数量
        private String follower_count;
        //消息ID
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getKsyun_id() {
            return ksyun_id;
        }

        public void setKsyun_id(int ksyun_id) {
            this.ksyun_id = ksyun_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public boolean isIs_followed() {
            return is_followed;
        }

        public void setIs_followed(boolean is_followed) {
            this.is_followed = is_followed;
        }

        public String getVideo_count() {
            return video_count;
        }

        public void setVideo_count(String video_count) {
            this.video_count = video_count;
        }

        public String getFollower_count() {
            return follower_count;
        }

        public void setFollower_count(String follower_count) {
            this.follower_count = follower_count;
        }
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCover_url() {
            return cover_url;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        public boolean is_followed() {
            return is_followed;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getRelated_member_id() {
            return related_member_id;
        }

        public void setRelated_member_id(String related_member_id) {
            this.related_member_id = related_member_id;
        }
    }
}
