package qge.cn.com.qgenglish.app;

import java.io.Serializable;
import java.util.Map;

/**
 * @ Author Well
 * @ Date 2016/12/22 11:18
 * @ Description: // 统一API响应结果封装
 */
public class Result<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public enum ResultCode {
        SUCCESS(200),//成功
        FAIL(400),//失败
        UNAUTHORIZED(401),//未认证（签名错误）
        NOT_FOUND(404),//接口不存在
        INTERNAL_SERVER_ERROR(500);//服务器内部错误
        public int code;

        ResultCode(int code) {
            this.code = code;
        }
    }

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

}
