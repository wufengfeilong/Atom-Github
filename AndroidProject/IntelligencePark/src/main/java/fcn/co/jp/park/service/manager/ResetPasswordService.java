package fcn.co.jp.park.service.manager;

import fcn.co.jp.park.util.PageData;

public interface ResetPasswordService {

    /**
     * 通过用户ID获取旧密码
     * @param pd
     * @return
     */
    PageData findPwdById(PageData pd);

    /**
     * 重置登录密码
     * @param pd
     * @return
     */
    int resetPwd(PageData pd);
}
