package com.project.presentation.shared.view;

import com.project.presentation.shared.dto.FotostudioDTO;
import com.project.presentation.shared.dto.LoginDTO;
import com.project.presentation.shared.dto.NotificationBean;

import java.io.Serializable;

/**
 * Created by john on 24.10.2015.
 */
public class AbstractView implements Serializable {
    private NotificationBean notificationBean;
    private FotostudioDTO studio;
    private LoginDTO login;

    public LoginDTO getLogin() {
        return login;
    }

    public void setLogin(LoginDTO login) {
        this.login = login;
    }

    public NotificationBean getNotificationBean() {
        return notificationBean;
    }

    public void setNotificationBean(NotificationBean notificationBean) {
        this.notificationBean = notificationBean;
    }

    public FotostudioDTO getStudio() {
        return studio;
    }

    public void setStudio(FotostudioDTO studio) {
        this.studio = studio;
    }
}
