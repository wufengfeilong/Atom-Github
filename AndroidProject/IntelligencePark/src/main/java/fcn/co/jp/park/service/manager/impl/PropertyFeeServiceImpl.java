package fcn.co.jp.park.service.manager.impl;

import fcn.co.jp.park.mapper.manager.PropertyFeeMapper;
import fcn.co.jp.park.service.manager.PropertyFeeService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service(value = "propertyFeeEditService")
public class PropertyFeeServiceImpl implements PropertyFeeService {

    @Autowired
    private PropertyFeeMapper propertyFeeMapper;

    @Override
    public Map<String, Object> propertyFeeEdit(PageData pd) throws Exception{
        Map<String, Object> returnMap = new HashMap<String, Object>();
        PageData syspd = new PageData();
        // 物业费表主键
        String ID = pd.getString("id");
        // 建筑面积
        String COMPANYSPACE = pd.getString("companyspace");
        // 物业单价
        String UNITPRICE = pd.getString("unitprice");
        // 开始日期
        String STARTDATE = pd.getString("startdate");
        // 截止日期
        String ENDDATE = pd.getString("enddate");
        // 物业折扣
        String DISCOUNT = pd.getString("discount");
        // 物业费
        String FEE = pd.getString("fee");
        // 备注
        String COMMENT = pd.getString("comment");
        syspd.put("ID", ID);
        syspd.put("COMPANYSPACE", COMPANYSPACE);
        syspd.put("UNITPRICE", UNITPRICE);
        syspd.put("STARTDATE", STARTDATE);
        syspd.put("ENDDATE", ENDDATE);
        syspd.put("DISCOUNT", DISCOUNT);
        syspd.put("FEE", FEE);
        syspd.put("COMMENT", COMMENT);
        int result = propertyFeeMapper.updateByPrimaryKey(syspd);
        if (result >= 0) {
            returnMap.put("result", true);
            returnMap.put("msg", "编辑成功");
            returnMap.put("data", result);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "编辑失败");
            returnMap.put("data", result);
            return returnMap;
        }
    }

    @Override
    public List<PageData> getPropertyFeeList(PageData pd){

        List<PageData> result = propertyFeeMapper.getPropertyFeeList(pd);
        return result;
    }

    @Override
    public Integer getPropertyFeeListCount(){

        Integer result = propertyFeeMapper.getPropertyFeeListCount();

        return result;
    }
}
