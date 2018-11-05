package com.project.presentation.client.eventbus;

import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.project.presentation.client.management.login.ILoginView;
import com.project.presentation.client.management.menu.IMenuView;
import com.project.presentation.client.management.page.IPageView;
import com.project.presentation.client.management.start.IStartView;
import com.project.presentation.client.management.studio.IManagementView;
import com.project.presentation.client.management.user.IUserView;

/**
 * Created by john on 27.06.2015.
 */
public interface ClientFactory {
    EventBus getEventBus();
    PlaceController getPlaceController();

    ILoginView getLoginView();
    IManagementView getManagementView();
    IUserView getUserView();
    IMenuView getMenuView();
    IPageView getPageView();
    IStartView getPageStart();
}
