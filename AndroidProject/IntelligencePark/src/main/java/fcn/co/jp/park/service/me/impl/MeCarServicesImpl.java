package fcn.co.jp.park.service.me.impl;

import fcn.co.jp.park.mapper.me.MeCarMapper;
import fcn.co.jp.park.service.me.MeCarService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "meCarServices")
public class MeCarServicesImpl implements MeCarService {
    @Autowired
    private MeCarMapper mMeCarMapper;

    /**
     * 获取车辆列表所有的List内容
     *
     * @return
     */
    @Override
    public Map<String, Object> getCarList() {
        Map<String, Object> returnMap = new HashMap<>();
        List<PageData> result = mMeCarMapper.getCarList();
        if (result != null) {
            returnMap.put("getCarInfo", result);
            return returnMap;
        } else {
            returnMap.put("getCarInfo", result);
            return returnMap;
        }
    }

    /**
     * 修改我的车辆信息
     *
     * @return
     */
    @Override
    public Map<String, Object> getCarEditor(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        int result = mMeCarMapper.updateCarList(pd);
        if (result > 0) {
            returnMap.put("result", true);
            returnMap.put("msg", "修改成功～");
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "修改失败～");
        }
        return returnMap;
    }

    /**
     * 我的车辆信息
     *
     * @return
     */
    @Override
    public Map<String, Object> getAddCar(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        int result = mMeCarMapper.insertCarList(pd);
        if (result > 0) {
            returnMap.put("result", true);
            returnMap.put("msg", "追加成功～");
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "追加失败～");
        }
        return returnMap;
    }

    /**
     * 根据车辆列表Id删除相应的条目
     *
     * @return
     */
    @Override
    public int deleteCarById(PageData pd) {
        int result = mMeCarMapper.deleteCarById(pd);
        return result;
    }


}

