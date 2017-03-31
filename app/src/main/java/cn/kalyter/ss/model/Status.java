/**
 * Created by Kalyter on 2016/9/30.
 */
package cn.kalyter.ss.model;

public enum Status {
    OK("OK",200),
    ERROR("ERROR",500),
    ;

    private String message;

    private int code;

    Status(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
