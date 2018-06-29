package fcn.co.jp.park.service.me;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:51
 */
public interface PersonService {
    /**
     * 个人信息查询
     *
     * @param pd
     */
    PageData getPersonalInfo(PageData pd);

    /**
     * 修改个人信息
     *
     * @param pd
     */
    Map<String, Object> updatePersonalInfo(PageData pd);

    /**
     * 修改头像
     *
     * @param pd
     */
    int updateUserPicture(PageData pd);
}
