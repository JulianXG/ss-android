/**
 * Created by Kalyter on 2016/9/30.
 */
package cn.kalyter.ss.model;


public class Response<T> {

    private String message;

    private Long code;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
