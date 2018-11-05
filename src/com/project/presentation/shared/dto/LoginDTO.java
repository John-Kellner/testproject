package com.project.presentation.shared.dto;

import com.project.presentation.shared.enumerations.ELoginState;
import com.project.presentation.shared.enumerations.EUser;

import java.io.Serializable;

/**
 * Created by john on 24.10.2015.
 */
public class LoginDTO implements Serializable {
    private Long userID;
    private String email;
    private String password;
    private ELoginState state = ELoginState.LOGIN;
    private EUser user;

    public LoginDTO() {}

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public EUser getUserTyp() {
        return user;
    }

    public void setUserTyp(EUser user) {
        this.user = user;
    }

    public ELoginState getState() {
        return state;
    }

    public void setState(ELoginState state) {
        this.state = state;
    }
}
