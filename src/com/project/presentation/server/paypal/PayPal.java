package com.project.presentation.server.paypal;

import com.project.presentation.shared.dto.NotificationBean;
import com.project.presentation.shared.enumerations.ENotify;

/**
 * Created by john on 15.11.2015.
 */
public class PayPal {

    public NotificationBean login(String email, String password) {
        final NotificationBean notification = new NotificationBean();

        notification.setNotify(ENotify.FAILURE);
        return notification;
    }
}
