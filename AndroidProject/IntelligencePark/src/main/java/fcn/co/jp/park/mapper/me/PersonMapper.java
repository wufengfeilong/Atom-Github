package fcn.co.jp.park.mapper.me;

import fcn.co.jp.park.util.PageData;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:47
 */
public interface PersonMapper {
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
    void updatePersonalInfo(PageData pd);

    /**
     * 修改头像
     *
     * @param pd
     * @return
     */
    int updateUserPicture(PageData pd);

    /**
     * 通过phone获取个人数据
     *
     * @param pd
     * @return
     * @throws Exception
     */
    PageData findByPhone(PageData pd);

    /**
     * 通过phone获取企业数据
     *
     * @param pd
     * @return
     * @throws Exception
     */
    PageData findBycompanyPhone(PageData pd);
}
