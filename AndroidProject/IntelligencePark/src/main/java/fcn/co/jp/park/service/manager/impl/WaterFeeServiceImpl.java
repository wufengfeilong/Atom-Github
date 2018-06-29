package fcn.co.jp.park.service.manager.impl;

import fcn.co.jp.park.mapper.manager.WaterFeeMapper;
import fcn.co.jp.park.service.manager.WaterFeeService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "waterFeeService")
public class WaterFeeServiceImpl implements WaterFeeService {

    @Autowired
    private WaterFeeMapper waterFeeMapper;

    @Override
    public Map<String, Object> waterFeeEdit(PageData pd) throws Exception{
        Map<String, Object> returnMap = new HashMap<String, Object>();
        PageData syspd = new PageData();
        // 水费表主键
        String ID = pd.getString("id");
        // 开始码
        String STARTNUM = pd.getString("startnum");
        // 截止码
        String ENDNUM = pd.getString("endnum");
        // 使用总量
        String COSTNUM = pd.getString("costnum");
        // 记录日期
        String RECORDDATE = pd.getString("recorddate");
        // 单价
        String UNITPRICE = pd.getString("unitprice");
        // 水费
        String FEE = pd.getString("fee");
        syspd.put("ID", ID);
        syspd.put("STARTNUM", STARTNUM);
        syspd.put("ENDNUM", ENDNUM);
        syspd.put("COSTNUM", COSTNUM);
        syspd.put("RECORDDATE", RECORDDATE);
        syspd.put("UNITPRICE", UNITPRICE);
        syspd.put("FEE", FEE);
        int result = waterFeeMapper.updateByPrimaryKey(syspd);
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
    public List<PageData> getWaterFeeList(PageData pd){

        List<PageData> result = waterFeeMapper.getWaterFeeList(pd);
        return result;
    }

    @Override
    public Integer getWaterFeeListCount(){

        Integer result = waterFeeMapper.getWaterFeeListCount();

        return result;
    }
}
