package com.fcn.park.message.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fcn.park.base.http.ParamNames;

import java.util.List;

/**
 * Created by 860115001 on 2018/04/11.
 */

public class SystemMessageBean {

    /**
     * messageList : []
     * totalPage : 0
     * isNext : false
     */

    private String totalPage;
    private boolean isNext;
    private List<MessageListBean> messageList;

    public static class MessageListBean implements Parcelable {

        private String title;
        @ParamNames("content")
        private String message;
        @ParamNames("create_time")
        private String time;
        /**
         * update_time : 2017-06-08 10:54
         * user_type : 0
         * update_user : 1
         * create_time : 2017-06-08 10:54
         * is_del : 0
         * message_id : 1144
         * create_user : 1
         * content : 啦啦啦
         */

        private String update_time;
        private String user_type;
        private String update_user;
        private String is_del;
        private int message_id;
        private String create_user;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }


        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUpdate_user() {
            return update_user;
        }

        public void setUpdate_user(String update_user) {
            this.update_user = update_user;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public int getMessage_id() {
            return message_id;
        }

        public void setMessage_id(int message_id) {
            this.message_id = message_id;
        }

        public String getCreate_user() {
            return create_user;
        }

        public void setCreate_user(String create_user) {
            this.create_user = create_user;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.message);
            dest.writeString(this.time);
            dest.writeString(this.update_time);
            dest.writeString(this.user_type);
            dest.writeString(this.update_user);
            dest.writeString(this.is_del);
            dest.writeInt(this.message_id);
            dest.writeString(this.create_user);
        }

        public MessageListBean() {
        }

        protected MessageListBean(Parcel in) {
            this.title = in.readString();
            this.message = in.readString();
            this.time = in.readString();
            this.update_time = in.readString();
            this.user_type = in.readString();
            this.update_user = in.readString();
            this.is_del = in.readString();
            this.message_id = in.readInt();
            this.create_user = in.readString();
        }

        public static final Parcelable.Creator<MessageListBean> CREATOR = new Parcelable.Creator<MessageListBean>() {
            @Override
            public MessageListBean createFromParcel(Parcel source) {
                return new MessageListBean(source);
            }

            @Override
            public MessageListBean[] newArray(int size) {
                return new MessageListBean[size];
            }
        };
    }


    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isIsNext() {
        return isNext;
    }

    public void setIsNext(boolean isNext) {
        this.isNext = isNext;
    }

    public List<MessageListBean> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageListBean> messageList) {
        this.messageList = messageList;
    }
}
