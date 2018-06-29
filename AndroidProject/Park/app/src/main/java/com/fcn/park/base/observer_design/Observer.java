package com.fcn.park.base.observer_design;

/**
 * Created by liuyq on 2017/04/10.
 */

public interface Observer {
    void update(String message, boolean isResetting);
}
