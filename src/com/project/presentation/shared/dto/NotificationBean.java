package com.project.presentation.shared.dto;

import com.project.presentation.shared.enumerations.ENotify;

import java.io.Serializable;

/**
 * Created by john on 22.06.2015.
 */
public class NotificationBean implements Serializable {

    private ENotify notify;
    private String message;

    public void setNotify(ENotify notify) {
        this.notify = notify;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public ENotify getNotify() {
        return notify;
    }
}
