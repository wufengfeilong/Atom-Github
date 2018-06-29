package fcn.co.jp.park.mapper;

import fcn.co.jp.park.model.User;
import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    /**
     * 追加用户
     * @param pd
     * @return
     */
    int insertUser(PageData pd);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    /**
     * 通过手机号和密码查询用户是否存在
     * @param pd
     * @return
     */
    PageData getUserByPhoneAndPwd(PageData pd);

    /**
     * 验证码是否有效
     * @param pd
     * @return
     */
    int isVerificationCode(PageData pd);

    /**
     * 查询手机是否已经注册
     * @param pd
     * @return
     */
    int findByPhone(PageData pd);

    /**
     * 保存验证码
     * @param pd
     * @return
     */
    int insertCode(PageData pd);

    /**
     * 更新验证码
     * @param pd
     * @return
     */
    int updateCode(PageData pd);

    /**
     * 查询验证码时效
     * @param pd
     * @return
     */
    PageData findCodeVerification(PageData pd);

    /**
     * 查询注册条款
     * @param pd
     * @return
     */
    PageData findTerms(PageData pd);

    /**
     * 重置密码
     * @param pd
     * @return
     */
    int changePwdByPhone(PageData pd);

}