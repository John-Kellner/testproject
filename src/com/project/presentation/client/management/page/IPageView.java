package com.project.presentation.client.management.page;

import com.google.gwt.user.client.ui.IsWidget;
import com.project.presentation.client.management.page.activity.IPageActivity;

/**
 * Created by john on 28.07.2015.
 */
public interface IPageView extends IsWidget{
    /** Vorsicht activity steht nich im Konstruktor der abgeleiteten Klassen bereit */
    void setActivity(IPageActivity pageActivity);
}
