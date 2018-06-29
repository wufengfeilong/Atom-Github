package fcn.co.jp.park.service.me.impl;

import fcn.co.jp.park.mapper.me.PersonMapper;
import fcn.co.jp.park.service.me.PersonService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:52
 */
@Service(value = "personService")
public class PersonImpl implements PersonService {

    @Autowired
    private PersonMapper personMapper;
    /**
     * 个人信息查询
     *
     * @param pd
     */
    public PageData getPersonalInfo(PageData pd){
        return personMapper.getPersonalInfo(pd);
    }

    /**
     * 修改个人信息
     *
     * @param pd
     */
    public Map<String, Object> updatePersonalInfo(PageData pd) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try{
            if (personMapper.findByPhone(pd) != null || personMapper.findBycompanyPhone(pd) != null) {
                returnMap.put("result", false);
                returnMap.put("msg", "该手机号已注册");
                return returnMap;
            }

            personMapper.updatePersonalInfo(pd);
            returnMap.put("result", true);
            returnMap.put("msg", "修改成功");
        }catch(Exception e){
            returnMap.put("result", false);
            returnMap.put("msg", "修改错误");
        }
        return returnMap;
    }

    /**
     * 修改头像
     *
     * @param pd
     */
    public int updateUserPicture(PageData pd){
        return personMapper.updateUserPicture(pd);
    }
}
