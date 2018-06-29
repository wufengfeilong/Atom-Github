package fcn.co.jp.park.util;

import fcn.co.jp.park.service.common.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Component
public class FileUtil {

    @Autowired
    private FileService fileService;


    /**
     * 文件上传方法，返回fileId
     * @param pictureType
     * @param file
     * @return
     */
    public String fileUp(String pictureType, MultipartFile file) {

        String filePath = Const.FILEPATH + pictureType;

        File fileFolder = new File(filePath);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }

        String fileName = DateUtil.getTimeNoSign() + "_" + file.getOriginalFilename();
        String fileRealPath = filePath + fileName;

        PageData pd = new PageData();
        pd.put("path", fileName);
        String fileId = "";
        if (!file.isEmpty()) {
            try {
                uploadFile(file.getBytes(), fileRealPath);
            } catch (Exception e) {
                e.printStackTrace();
            }

            fileService.addImg(pd);
            fileId = String.valueOf(pd.get("picture_id"));
        }
        return fileId;
    }

    /**
     * 文件上传方法，返回存储路径
     * @param pictureType
     * @param file
     * @return
     */
    public String fileUpReturnPath(String pictureType, MultipartFile file) {

        String filePath = Const.FILEPATH + pictureType;

        File fileFolder = new File(filePath);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }

        String fileName = DateUtil.getTimeNoSign() + "_" + file.getOriginalFilename();
        String fileRealPath = filePath + fileName;

        PageData pd = new PageData();
        pd.put("path", fileName);
        if (!file.isEmpty()) {
            try {
                uploadFile(file.getBytes(), fileRealPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }


    /**
     * 根据fileId获取文件路径
     * @param fileId
     * @return
     */
    public String getFilePath(int fileId){
            return fileService.getFilePath(fileId);
    }


    /**
     * 文件存储方法
     * @param file
     * @param filePath
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath) throws Exception {
//        File targetFile = new File(filePath);
//        if(!targetFile.exists()){
//            targetFile.mkdirs();
//        }
        FileOutputStream out = new FileOutputStream(filePath);
        out.write(file);
        out.flush();
        out.close();
    }
}
