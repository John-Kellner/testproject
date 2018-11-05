package com.project.presentation.client.management.studio;

import com.google.gwt.user.client.ui.IsWidget;
import com.project.presentation.client.management.studio.activity.IManagementActivity;

/**
 * Created by john on 27.06.2015.
 */
public interface IManagementView extends IsWidget{
    void setActivity(IManagementActivity navigation);
    String getName();
}
