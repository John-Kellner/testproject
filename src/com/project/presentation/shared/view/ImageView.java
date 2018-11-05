package com.project.presentation.shared.view;

import com.project.presentation.shared.dto.NotificationBean;

import java.io.Serializable;

/**
 * Created by john on 24.07.2015.
 */
public class ImageView implements Serializable {

    private long id;
    private NotificationBean notification;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NotificationBean getNotification() {
        return notification;
    }

    public void setNotification(NotificationBean notification) {
        this.notification = notification;
    }
}
