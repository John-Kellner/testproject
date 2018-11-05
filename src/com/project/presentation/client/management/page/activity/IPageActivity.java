package com.project.presentation.client.management.page.activity;

import com.project.presentation.client.eventbus.impl.navigation.INavigation;
import com.project.presentation.client.management.EManagedPlace;
import com.project.presentation.client.management.page.IPageView;
import com.project.presentation.client.management.page.activity.impl.PageActivity;

/**
 * Created by john on 28.07.2015.
 */
public interface IPageActivity extends INavigation{
    String getTokenName();
}
