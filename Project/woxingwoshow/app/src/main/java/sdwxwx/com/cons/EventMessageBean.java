package sdwxwx.com.cons;

import java.io.Serializable;

/**
 * Event消息类
 * Created by lily on 25/5/2018.
 */
public class EventMessageBean implements Serializable {
    // 标示
    private String tag;
    // 如果是list，list的位置
    private int position;
    // 发送任意对象
    private Object object;

    public EventMessageBean(int position, Object object) {
        this.position = position;
        this.object = object;
    }

    public EventMessageBean(String tag, Object object) {
        this.tag = tag;
        this.object = object;
    }

    public EventMessageBean(String tag) {
        this.tag = tag;
    }

    public EventMessageBean(String tag, int position) {
        this.tag = tag;
        this.position = position;
    }

    public EventMessageBean(String tag, int position, Object object) {
        this.tag = tag;
        this.position = position;
        this.object = object;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
