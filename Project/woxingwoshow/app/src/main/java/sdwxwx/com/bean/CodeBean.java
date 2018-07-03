package sdwxwx.com.bean;

import java.io.Serializable;

/**
 * Created by 丁胜胜 on 2018/06/04.
 * 类描述：获得验证码实体类
 */

public class CodeBean implements Serializable {

    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
