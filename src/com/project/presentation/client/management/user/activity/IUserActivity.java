package com.project.presentation.client.management.user.activity;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.presentation.client.eventbus.impl.navigation.INavigation;
import com.project.presentation.shared.dto.NotificationBean;
import com.project.presentation.shared.dto.UserDTO;

/**
 * Created by john on 28.06.2015.
 */
public interface IUserActivity extends INavigation{
    void saveUser(UserDTO user, AsyncCallback<NotificationBean> asyncCallback);
}
