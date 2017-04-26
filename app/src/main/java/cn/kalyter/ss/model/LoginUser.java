/**
 * Created by Kalyter on 2016/10/26.
 */
package cn.kalyter.ss.model;

import java.io.Serializable;

public class LoginUser implements Serializable {
    private String username;
    private String password;
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
