package fcn.co.jp.park.service.common.impl;

import fcn.co.jp.park.mapper.common.FileMapper;
import fcn.co.jp.park.mapper.me.CompanyMapper;
import fcn.co.jp.park.service.common.FileService;
import fcn.co.jp.park.service.me.CompanyService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:52
 */
@Service(value = "fileServiceImpl")
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;

    @Override
    public int addImg(PageData pd) {
        return fileMapper.insertImge(pd);
    }

    @Override
    public String getFilePath(int fileId) {
        return fileMapper.getFilePath(fileId);
    }
}
