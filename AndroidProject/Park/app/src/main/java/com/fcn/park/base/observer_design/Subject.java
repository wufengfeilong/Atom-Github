package com.fcn.park.base.observer_design;

/**
 * Created by liuyq on 2018/4/10.
 */

public interface Subject {
    /**
     * 增加订阅者
     * @param observer
     */
    void attach(Observer observer);
    /**
     * 删除订阅者
     * @param observer
     */
    void detach(Observer observer);
    /**
     * 通知订阅者更新消息
     */
    void notify(String message, boolean isResetting);
}
