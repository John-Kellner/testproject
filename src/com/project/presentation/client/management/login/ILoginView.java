package com.project.presentation.client.management.login;

import com.google.gwt.user.client.ui.IsWidget;
import com.project.presentation.client.management.login.activity.ILoginActivity;

/**
 * Created by john on 27.06.2015.
 */
public interface ILoginView extends IsWidget{
    void setActivity(ILoginActivity navigation);
    String getName();
}
