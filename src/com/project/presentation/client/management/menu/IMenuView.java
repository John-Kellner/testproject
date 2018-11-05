package com.project.presentation.client.management.menu;

import com.google.gwt.user.client.ui.IsWidget;
import com.project.presentation.client.management.menu.activity.impl.MenuActivity;
import com.project.presentation.shared.view.UserView;

/**
 * Created by john on 28.06.2015.
 */
public interface IMenuView extends IsWidget{
    void setActivity(MenuActivity menuActivity);
    void setView(UserView view);
}
