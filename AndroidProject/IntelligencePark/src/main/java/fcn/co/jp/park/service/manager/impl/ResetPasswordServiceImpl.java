package fcn.co.jp.park.service.manager.impl;

import fcn.co.jp.park.mapper.manager.ResetPasswordMapper;
import fcn.co.jp.park.service.manager.ResetPasswordService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "resetPasswordService")
public class ResetPasswordServiceImpl implements ResetPasswordService {

    @Autowired
    private ResetPasswordMapper mResetPasswordMapper;

    /**
     * 通过用户ID，获取旧密码
     * @param pd
     * @return
     */
    @Override
    public PageData findPwdById(PageData pd) {
        return mResetPasswordMapper.findPwdById(pd);
    }

    /**
     * 重置密码
     * @param pd
     * @return
     */
    @Override
    public int resetPwd(PageData pd) {
        return mResetPasswordMapper.resetPwd(pd);
    }
}
