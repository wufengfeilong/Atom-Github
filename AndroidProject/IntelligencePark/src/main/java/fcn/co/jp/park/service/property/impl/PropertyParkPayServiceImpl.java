package fcn.co.jp.park.service.property.impl;

import fcn.co.jp.park.mapper.property.PropertyParkPayMapper;
import fcn.co.jp.park.service.property.PropertyParkPayService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(value = "parkPayService")
public class PropertyParkPayServiceImpl implements PropertyParkPayService{

    @Autowired
    private PropertyParkPayMapper propertyParkPayMapper;


    /**
     * 根据车牌号判断该车辆是否在使用
     * @param carNumber
     * @return
     */
    @Override
    public int checkCarNumber(String carNumber) {
        int result = propertyParkPayMapper.checkCarNumber(carNumber);
        return result;
    }

    /**
     * 获取月租车辆申请画面初始化数据
     * @return
     */
    @Override
    public Map<String, Object> getInitData() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //查询申请类型
        PageData pd = new PageData();
        pd.put("TYPE","PARK_PAY_TYPE");
        List<PageData> returnData = propertyParkPayMapper.getInitData(pd);
        if(returnData!=null&&returnData.size()==3){
            returnMap.put("result", true);
            returnMap.put("data", returnData);
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "获取数据失败");
        }
        return returnMap;
    }

    /**
     * 添加月租车辆申请
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> addparkPayApply(PageData pd) {

        //数据库添加申请
        int result = propertyParkPayMapper.addParkPayApply(pd);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if(result>0){
            returnMap.put("result", true);
            returnMap.put("msg", "申请已提交");
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "申请提交失败");
        }
        return returnMap;
    }


    /**
     * 获取月租车辆驳回的申请的详细信息
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> getRejectInitData(PageData pd) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //查询数据库驳回通知中的驳回数据信息
        PageData returnData = propertyParkPayMapper.getRejectInitData(pd);
        if(returnData!=null){
            returnMap.put("result", true);
            returnMap.put("data",returnData);
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "获取数据失败");
        }
        return returnMap;
    }
}
