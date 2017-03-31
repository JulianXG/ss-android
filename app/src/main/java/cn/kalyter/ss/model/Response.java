/**
 * Created by Kalyter on 2016/9/30.
 */
package cn.kalyter.ss.model;

public class Response<T> {

    private Status status;

    private T data;

    public Response(T data,Status status) {
        this.status = status;
        this.data = data;
    }

    public Response() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
