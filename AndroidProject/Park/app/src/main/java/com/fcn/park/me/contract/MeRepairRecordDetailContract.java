package com.fcn.park.me.contract;

import android.support.annotation.StringDef;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.DetailInfoBean;
import com.fcn.park.me.bean.RepairRecordDetailBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 类描述：报修列表详情用Contract.
 */

public interface MeRepairRecordDetailContract {

    interface View extends BaseView {
        void updateInfo(RepairRecordDetailBean bean);
        String getRepairId();
    }

    interface Presenter {
        void loadInfo();
    }
}
