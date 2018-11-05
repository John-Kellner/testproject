package com.project.presentation.client.management.user;

import com.google.gwt.user.client.ui.IsWidget;
import com.project.presentation.client.management.user.activity.impl.UserActivity;
import com.project.presentation.shared.view.StudioView;

/**
 * Created by john on 28.06.2015.
 */
public interface IUserView extends IsWidget {
    void setActivity(UserActivity userActivity);
    void setView(StudioView view);
}
