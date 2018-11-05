package com.project.presentation.shared.dto;

import java.io.Serializable;

/**
 * Created by john on 17.07.2016.
 */
public class AnlageDTO implements Serializable {
    private long id;
    private NotificationBean notification;
    private String filename;
    private long einstellungID;

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

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setEinstellungID(long einstellungID) {
        this.einstellungID = einstellungID;
    }

    public String getFilename() {
        return filename;
    }

    public long getEinstellungID() {
        return einstellungID;
    }
}
