package com.fcn.park.property.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.property.contract.PropertyServiceContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 860117073 on 2018/5/4.
 */

public class PropertyServicePresenter extends BasePresenter<PropertyServiceContract.View> implements PropertyServiceContract.Presenter {

    @Override
    public void attach(PropertyServiceContract.View view) {
        super.attach(view);
    }

    public List getData() {
        String[] names = new String[]{"送水服务", "搬家服务", "清洗服务", "绿植租赁服务"};
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++) {
            Map map = new HashMap<String, Object>();
            map.put("name", names[i]);
            list.add(map);
        }
        return list;
    }

}
