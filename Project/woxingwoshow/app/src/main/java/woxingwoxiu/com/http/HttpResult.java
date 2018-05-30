package woxingwoxiu.com.http;

/**
 * create by 860115039
 * date      2018/5/8
 * time      12:45
 */
public class HttpResult<T> {
    private boolean result;
    private int code;
    private int flag = Integer.MIN_VALUE;
    private String message;
    private T data;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
