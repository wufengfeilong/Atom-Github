package fcn.co.jp.park.service.impl;

import fcn.co.jp.park.mapper.UserMapper;
import fcn.co.jp.park.model.User;
import fcn.co.jp.park.service.UserService;
import fcn.co.jp.park.util.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    @Override
    public Map<String, Object> addUser(PageData pd) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        int result = userMapper.insertUser(pd);
        if (result > 0){
            returnMap.put("result", true);
            returnMap.put("msg", "注册成功");
        } else {
            returnMap.put("result", true);
            returnMap.put("msg", "注册成功");
        }
        return returnMap;
    }

    /**
     * 用户登录
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> userLogin(PageData pd) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        PageData param = new PageData();
        String phone = pd.getString("account");
        param.put("phone", phone);
        // 密码加密
        param.put("password", MD5.md5(pd.getString("pwd")));
        PageData result = userMapper.getUserByPhoneAndPwd(param);
        if (result != null) {
            data.put("userType", result.get("user_type"));
            data.put("user_id", result.get("user_id"));
            data.put("user_name",  result.get("user_name"));
            data.put("img_path", result.get("img_path"));
            returnMap.put("result", true);
            returnMap.put("msg", "登录成功");
            returnMap.put("data", data);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "手机号或密码错误，请重新登录");
            returnMap.put("data", data);
            return returnMap;
        }
    }

    @Override
    public Map<String, Object> sendCodeByTel(PageData pd, String random) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        // 判断手机是否存在
        if (findByPhone(pd) > 0) {
            Map<String, Object> data = new HashMap<String, Object>();
//            SendSmsMsg.smsCodeSend(pd.getString("phone"), MessageFormat.format(SendSmsMsg.verifyCodeMsg, random));
            pd.put("random", random);
            pd.put("valType", pd.getString("phone"));
            this.saveCode(pd);
            data.put("random", random);
            returnMap.put("result", true);
            returnMap.put("data", data);
            return returnMap;
        }
        returnMap.put("msg", "手机未被绑定");
        returnMap.put("result", false);
        return returnMap;
    }

    /**
     * 验证码处理
     *
     * @return
     * @throws Exception
     */
    @Override
    public void saveCode(PageData pd) {
        int count = userMapper.isVerificationCode(pd);
        if (0 == count) {
            userMapper.insertCode(pd);
        } else {
            userMapper.updateCode(pd);
        }
    }

    /**
     * 查询手机是否已经注册
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public int findByPhone(PageData pd) {
        return userMapper.findByPhone(pd);
    }

    /**
     * 获取验证码时效
     * @param pd
     * @return
     */
    @Override
    public PageData verifyCode(PageData pd) {
        return userMapper.findCodeVerification(pd);
    }

    /**
     * 获取注册条款
     * @param pd
     * @return
     */
    @Override
    public PageData findTerms(PageData pd) {
        return userMapper.findTerms(pd);
    }

    /**
     * 忘记密码 --> 重置密码
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> editPwdByTel(PageData pd) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        pd.put("pwd", MD5.md5(pd.getString("pwd")));
        int result = userMapper.changePwdByPhone(pd);
        if (result > 0) {
            returnMap.put("msg", "已成功更改密码");
            returnMap.put("result", true);
        } else {
            returnMap.put("msg", "重置密码失败");
            returnMap.put("result", false);
        }

        return returnMap;
    }
}
