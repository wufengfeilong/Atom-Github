package fcn.co.jp.park.service.property.impl;

import fcn.co.jp.park.mapper.manager.PostNewsMapper;
import fcn.co.jp.park.mapper.property.BusinessMapper;
import fcn.co.jp.park.service.property.BusinessServices;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(value = "businessServices")
public class BusinessServicesImpl implements BusinessServices {

    @Autowired
    private BusinessMapper mBusinessMapper;

    /**
     * 获取送水商家列表所有的List内容
     *
     * @return
     */
    @Override
    public Map<String, Object> getDeliverWaterList(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        List<PageData> result = mBusinessMapper.getDeliverWaterList(pd);
        if (result != null) {
            returnMap.put("getDeliverWater", result);
            return returnMap;
        } else {
            returnMap.put("getDeliverWater", result);
            return returnMap;
        }
    }

    /**
     * 获取搬家商家列表所有的List内容
     *
     * @return
     */
    @Override
    public Map<String, Object> getMoveHouseList(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        List<PageData> result = mBusinessMapper.getMoveHouseList(pd);
        if (result != null) {
            returnMap.put("getMoveHouse", result);
            return returnMap;
        } else {
            returnMap.put("getMoveHouse", result);
            return returnMap;
        }
    }

    /**
     * 获取清洗商家列表所有的List内容
     *
     * @return
     */
    @Override
    public Map<String, Object> getCleanList(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        List<PageData> result = mBusinessMapper.getCleanList(pd);
        if (result != null) {
            returnMap.put("getClean", result);
            return returnMap;
        } else {
            returnMap.put("getClean", result);
            return returnMap;
        }
    }

    /**
     * 获取绿植租赁商家列表所有的List内容
     *
     * @return
     */
    @Override
    public Map<String, Object> getPlantLeaseList(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        List<PageData> result = mBusinessMapper.getPlantLeaseList(pd);
        if (result != null) {
            returnMap.put("getPlantLease", result);
            return returnMap;
        } else {
            returnMap.put("getPlantLease", result);
            return returnMap;
        }
    }

    /**
     * 获取送水列表总件数
     *
     * @return
     */
    @Override
    public int getDeliverWaterListCount() {

        Integer result = mBusinessMapper.getDeliverWaterListCount();
        return result;
    }

    /**
     * 获取搬家列表总件数
     *
     * @return
     */
    @Override
    public int getMoveHouseListCount() {

        Integer result = mBusinessMapper.getMoveHouseListCount();
        return result;
    }

    /**
     * 获取清洁列表总件数
     *
     * @return
     */
    @Override
    public int getCleanListCount() {

        Integer result = mBusinessMapper.getCleanListCount();
        return result;
    }

    /**
     * 获取绿植租赁列表总件数
     *
     * @return
     */
    @Override
    public int getPlantLeaseListCount() {

        Integer result = mBusinessMapper.getPlantLeaseListCount();
        return result;
    }

}
