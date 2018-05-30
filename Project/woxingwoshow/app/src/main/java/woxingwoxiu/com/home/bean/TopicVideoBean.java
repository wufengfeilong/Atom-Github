package woxingwoxiu.com.home.bean;

/**
 * Created by 860117073 on 2018/5/29.
 * 话题视频列表
 */

public class TopicVideoBean {
    int ksyun_id; //金山云id
    String title; //视频标题
    String cover_url; //视频封面url
    String nickname; //作者昵称
    String avatar_url; //头像的url

    public int getKsyun_id() {
        return ksyun_id;
    }

    public void setKsyun_id(int ksyun_id) {
        this.ksyun_id = ksyun_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
