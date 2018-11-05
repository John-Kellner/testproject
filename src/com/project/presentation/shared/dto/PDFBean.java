package com.project.presentation.shared.dto;

import java.io.Serializable;

/**
 * Created by john on 18.11.2015.
 */
public class PDFBean implements Serializable {

    private String pdfName;
    private NotificationBean notification;

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public NotificationBean getNotification() {
        return notification;
    }

    public void setNotification(NotificationBean notification) {
        this.notification = notification;
    }
}
