package fcn.co.jp.park.service.manager.impl;
import fcn.co.jp.park.mapper.manager.EnterpriseCheckMapper;
import fcn.co.jp.park.service.manager.EnterpriseCheckService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "enterpriseCheckService")

/**
 * 企业审核列表
 */
public class EnterpriseCheckServiceImpl implements EnterpriseCheckService {

    @Autowired
    private EnterpriseCheckMapper enterpriseCheckMapper;

    /**
     * 企业审核列表
     * @return
     */
    @Override
    public Map<String, Object> selectEnterpriseCheckList() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<PageData> result = enterpriseCheckMapper.selectEnterpriseCheckList();
        if (result != null) {
            returnMap.put("enterpriseCheckList", result);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询失败");
            return returnMap;
        }
    }

    /**
     * 根据企业ID，获取企业审核详情
     *
     * @param pd：
     * @return
     */
    @Override
    public Map<String, Object> getEnterpriseCheckDetailById(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        PageData param = new PageData();
      /*  String id = pd.getString("id");*/
        param.put("id", 13); // 此处的id要和给Mapper.xml中传值的字段名保持一致
        PageData result = enterpriseCheckMapper.getEnterpriseCheckDetailById(param);
        if (result != null) {
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("data", result);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询失败");
            returnMap.put("data", data);
            return returnMap;
        }

    }

    /**
     * 企业审核通过往信息表里插入数据
     * @param pd
     * @return
     */
    @Override
    public int updateEnterpriseCheckParkStatus(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        PageData param = new PageData();
        /*String id = pd.getString("id");*/
      /*  param.put("id", 13);
        param.put("audit_result", "1");*/
        String updateUserId = pd.getString("updateUserId");
       /* param.put("updateUserId",updateUserId );*/
        int updateResult = enterpriseCheckMapper.updateEnterpriseCheckParkStatus(param);
        int resultPassMessage = -1;
      /*  String user_id = pd.getString("user_id");*/
        param.put("user_id","123456" );
        param.put("title", "企业审核通过");
        param.put("content", "您的企业已审核通过！");
        resultPassMessage=enterpriseCheckMapper.insertEnterpriseCheckPassMessage(param);
        int resultUpdateEnterpriseCheckUserRole = -1;
        resultUpdateEnterpriseCheckUserRole=enterpriseCheckMapper.UpdateEnterpriseCheckUserRole(param);
        return updateResult;
    }

    /**
     * 企业审核失败，往数据表里插入一条数据
     * @param pd
     * @return
     */
    @Override
    public int enterpriseCheckTurnClick(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        PageData param = new PageData();
        String id = pd.getString("13");
      /*  String audit_reason= pd.getString("audit_reason");
        param.put("audit_reason", audit_reason);*/
        param.put("id", id);
        int result = enterpriseCheckMapper.enterpriseCheckTurnClick(param);
        int insertTurnMessage = -1;
        String user_id = pd.getString("UserId");
        String updateUserId = pd.getString("updateUserId");
        param.put("user_id",user_id );
        param.put("updateUserId",updateUserId );
        param.put("title", "企业审核失败");
        param.put("content", "1234654565654");
        insertTurnMessage=enterpriseCheckMapper.insertEnterpriseCheckTurnMessage(param);
        return result;
    }
}