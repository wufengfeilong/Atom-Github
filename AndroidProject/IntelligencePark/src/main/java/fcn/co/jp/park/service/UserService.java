package fcn.co.jp.park.service;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

public interface UserService {

    /**
     * 用户注册
     * @param pd
     * @return
     */
    Map<String, Object> addUser(PageData pd);

    /**
     * 用户登录
     * @param pd
     * @return
     * @throws Exception
     */
    Map<String, Object> userLogin(PageData pd);

    /**
     * 忘记密码 --> 发送验证码
     * @param pd
     * @param random
     * @return
     * @throws Exception
     */
    Map<String, Object> sendCodeByTel(PageData pd, String random) throws Exception;

    /**
     * 保存验证码
     * @param pd
     * @throws Exception
     */
    void saveCode(PageData pd);

    /**
     * 查询手机是否已经注册
     * @param pd
     * @return
     * @throws Exception
     */
    int findByPhone(PageData pd);

    /**
     * 查询验证码时效
     * @param pd
     * @return
     * @throws Exception
     */
    PageData verifyCode(PageData pd);

    /**
     * 获取注册条款
     * @param pd
     * @return
     */
    PageData findTerms(PageData pd);

    /**
     * 忘记密码 --> 重置密码
     * @param pd
     * @return
     */
    Map<String, Object> editPwdByTel(PageData pd);

}
