package com.project.presentation.client.management.login.activity;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.project.presentation.client.eventbus.impl.navigation.INavigation;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.shared.view.AbstractView;
import com.project.presentation.shared.dto.LoginDTO;

/**
 * Created by john on 27.06.2015.
 */
public interface ILoginActivity extends INavigation {
    void login(LoginDTO user, boolean isAlwaysLoggedIn, AsyncCallback<AbstractView> callback);

    void show(EManagedPlace management, Object view);
}
