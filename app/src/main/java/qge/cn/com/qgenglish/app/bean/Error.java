package qge.cn.com.qgenglish.app.bean;

/**
 * Created by fony on 2017/12/22.
 * request 0
 */

public class Error {


    /**
     * request : 0
     * message : 成功失败信息
     */

    private int code;
    private String message;


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
}
