package fcn.co.jp.park.mapper.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface ResetPasswordMapper {

    /**
     * 通过用户ID，获取用户登录的旧密码
     * @param pd
     * @return
     */
    PageData findPwdById(PageData pd);

    /**
     * 重置密码
     * @param pd
     * @return
     */
    int resetPwd(PageData pd);
}
